package ap.example.annirbas.staxi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class comptetax extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Intent shareintent;
    private String sharebody="this is a greate app, you should try it out !";
private TextView textshow;
    String idobes,pswbserve;
    RadioButton non;
    RadioButton oui;
    String selection;
    private RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comptetax);
        oui=(RadioButton)findViewById(R.id.oui);
        non=(RadioButton)findViewById(R.id.non);
        rg = (RadioGroup) findViewById(R.id.radiogroup);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        idobes=(String) b.get("cod_tax");
        pswbserve=(String) b.get("psw");
        iin.putExtra("cod_tax",idobes);
        iin.putExtra("psw",pswbserve);
        Toast.makeText(comptetax.this,idobes,Toast.LENGTH_SHORT).show();
        Toast.makeText(comptetax.this,pswbserve,Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.comptetax, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent mes=new Intent(comptetax.this,mescoursesTax.class);
            mes.putExtra("cod_tax",idobes);
            Toast.makeText(comptetax.this,idobes,Toast.LENGTH_SHORT).show();
            startActivity(mes);

        } else if (id == R.id.nav_gallery) {
            Intent mes=new Intent(comptetax.this,ParamtereTax.class);
            mes.putExtra("psw",pswbserve);
            mes.putExtra("cod_tax",idobes);
            Toast.makeText(comptetax.this,idobes,Toast.LENGTH_SHORT).show();
            Toast.makeText(comptetax.this,pswbserve,Toast.LENGTH_SHORT).show();
            startActivity(mes);

        } else if (id == R.id.nav_slideshow) {
            Intent mes=new Intent(comptetax.this,panne.class);
            startActivity(mes);

        } else if (id == R.id.nav_share) {
            shareintent = new Intent( Intent.ACTION_SEND );
            shareintent.setType( "text/plain" );
            shareintent.putExtra( Intent.EXTRA_SUBJECT,"Servise Taxi" );
            shareintent.putExtra( Intent.EXTRA_TEXT,sharebody );
            startActivity( Intent.createChooser( shareintent,"Share Servise Taxi Via" ) );

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sexe(View view) {
        if (R.id.non == rg.getCheckedRadioButtonId()) {
            selection = (String) non.getText();
        } else {
            if (R.id.oui == rg.getCheckedRadioButtonId()) {
                selection = (String) oui.getText();

            }

        }
    }

    public void dis(View view) {
        String url="http://192.168.8.2/app/disponible.php?disponible="+  selection+"&Cod_tax="+idobes;
        new comptetax.MyAsyncTaskgetNews().execute(url);
        Toast.makeText(comptetax.this,"Vous avez choisi d'avoir du votre  disponibilité",Toast.LENGTH_SHORT).show();
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
