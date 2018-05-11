package ap.example.annirbas.staxi;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import android.widget.Toolbar;

public class demande_course extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    RadioButton homme;
    RadioButton femme;
    String selection;
    private RadioGroup rg;
    private Spinner sp;
    private EditText datedemn,datedep,heuredep,heuredemn;
    private Intent shareintent;
    private String sharebody="c'est une application géniale ,tu devrais l'essayer!";
    String idobesrve,nombserve,spgrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_demande_course );
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        idobesrve=(String) b.get("id");
        nombserve=(String) b.get("nom");
        Toast.makeText(demande_course.this,idobesrve,Toast.LENGTH_SHORT).show();
        Toast.makeText(demande_course.this,selection,Toast.LENGTH_SHORT).show();
        iin.putExtra("nom",nombserve);
        iin.putExtra("id",idobesrve);
        femme=(RadioButton)findViewById(R.id.femme);
        homme=(RadioButton)findViewById(R.id.homme);
        rg = (RadioGroup) findViewById(R.id.rg);
        sp=(Spinner)findViewById(R.id.sp);
        List list=new ArrayList();
        list.add("premium");
        list.add("Partagee");
        list.add("accessible");
        list.add("Economique");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spgrp=sp.getSelectedItem().toString();
                Toast.makeText(demande_course.this,spgrp,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        final Calendar c = Calendar.getInstance();
        final int year = c.get( Calendar.YEAR );
        final int month = c.get( Calendar.MONTH );
        final int day = c.get( Calendar.DAY_OF_MONTH );
        final int hour = c.get( Calendar.HOUR_OF_DAY );
        final int minute = c.get( Calendar.MINUTE );
        datedemn = (EditText) findViewById( R.id.datedemn );
        heuredemn = (EditText) findViewById( R.id.heuredemn );
        datedep = (EditText) findViewById( R.id.datedep );
        heuredep = (EditText) findViewById( R.id.heuredep);


        heuredemn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog1=new TimePickerDialog( demande_course.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourofday1, int minute2) {
                        heuredemn.setText( hourofday1 + ":" + minute2 );
                    }
                }, hour,minute,true);
                timePickerDialog1.setTitle( "selectionner le temps " );
                timePickerDialog1.show();
            }
        } );
        datedemn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog1 = new DatePickerDialog( demande_course.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year2, int monthofyear1, int  dayofmonth1) {
                        datedemn.setText( year2 + "-" + monthofyear1 + "-" + dayofmonth1 );
                    }
                },year,month,day );
                datePickerDialog1.setTitle( "Selectionner une date " );
                datePickerDialog1.show();
            }
        } );


        heuredep.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog( demande_course.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourofday, int minute1) {
                        heuredep.setText( hourofday + ":" + minute1 );
                    }
                }, hour, minute, true
                );
                timePickerDialog.setTitle( "selectionner le temps" );
                timePickerDialog.show();
            }
        } );



        datedep.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog( demande_course.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year1, int monthofyear, int dayofmonth) {
                        datedep.setText( year1 + "-" + monthofyear + "-" + dayofmonth );
                    }
                }, year, month, day );
                datePickerDialog.setTitle( "selectionner la date" );
                datePickerDialog.show();

            }
        } );



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
        getMenuInflater().inflate(R.menu.demande_course, menu);
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
            Intent mes=new Intent(demande_course.this,mescourse.class);
            mes.putExtra("id",idobesrve);
            startActivity(mes);
        } else if (id == R.id.nav_gallery) {
            Intent mes=new Intent(demande_course.this,Paramterecl.class);
            mes.putExtra("nom",nombserve);
            mes.putExtra("id",idobesrve);
            startActivity(mes);

        } else if (id == R.id.nav_slideshow) {



            Intent mes=new Intent(demande_course.this,Observationcl.class);
            mes.putExtra("id",idobesrve);
            startActivity(mes);

       }
        else if (id == R.id.nav_manage) {
            Intent mes=new Intent(demande_course.this,NoutezCl.class);
            mes.putExtra("id",idobesrve);
            startActivity(mes);

        } else if (id == R.id.nav_share) {
            shareintent = new Intent( Intent.ACTION_SEND );
            shareintent.setType( "text/plain" );
            shareintent.putExtra( Intent.EXTRA_SUBJECT,"Service Taxi" );
            shareintent.putExtra( Intent.EXTRA_TEXT,sharebody );
            startActivity( Intent.createChooser( shareintent,"partagez Service Taxi par" ) );

        } else if (id == R.id.nav_send) {
            Intent mes=new Intent(demande_course.this,fortaxins.class);
            startActivity(mes);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void envoyee(View view) {
        String url="http://192.168.8.2/app/addcourse.php?date_demande="+  datedemn.getText().toString()+"&heure_demande="+ heuredemn.getText().toString()
                +"&date_depart="+  datedep.getText().toString()+"&heure_depart="+heuredep.getText().toString()+"&Sex_tax_pref="+selection+"&cod_cl="+idobesrve+"&Tp_cou_pref"+spgrp+"&Tp_cou_pref="
                +spgrp;
        new demande_course.MyAsyncTaskgetNews().execute(url);
    }

  /*  public void mqap(View view) {
        Intent i=new Intent(demande_course.this,Maps.class);
        startActivity(i);
        Toast.makeText(demande_course.this, "Veuillez Activer votre GPS!", Toast.LENGTH_LONG).show();
    }*/

    public void sexe(View view) {
        if (R.id.homme == rg.getCheckedRadioButtonId()) {
            selection = (String) homme.getText();
        } else {
            if (R.id.femme == rg.getCheckedRadioButtonId()) {
                selection = (String) femme.getText();
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
               Toast.makeText(getApplicationContext(),progress[0],Toast.LENGTH_LONG).show();
               Intent i=new Intent(getApplicationContext(),Maps.class);
               startActivity(i);


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