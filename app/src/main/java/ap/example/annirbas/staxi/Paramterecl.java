package ap.example.annirbas.staxi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class Paramterecl extends Activity {
    private TextView btn1,btn2,btn3;
    String idobesrve,nombserve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_paramterecl );
        Intent i= getIntent();
        Bundle b = i.getExtras();
        idobesrve=(String) b.get("id");
        nombserve=(String) b.get("nom");
        i.putExtra("id",idobesrve);
        i.putExtra("nom",nombserve);
     //   Toast.makeText(Paramterecl.this,idobesrve,Toast.LENGTH_SHORT).show();
        // Toast.makeText(Paramterecl.this,nombserve,Toast.LENGTH_SHORT).show();
        btn1=(TextView)findViewById( R.id.btn1 );
        btn2=(TextView)findViewById( R.id.btn2);
        btn3=(TextView)findViewById( R.id.btn3 );
        btn1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent mes=new Intent(Paramterecl.this,Motpasseclmodifier.class);
        mes.putExtra("nom",nombserve);
        mes.putExtra("id",idobesrve);
        startActivity(mes);}});
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent neo = new Intent(Paramterecl.this, FAQ.class);
                startActivity(neo);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder( Paramterecl.this );
                builder.setTitle( "Quit !" );
                builder.setMessage( "Voulez-vous quitter l'application ?" );
               // builder.setIcon( R.drawable.icon );
                builder.setCancelable( true );


                builder.setPositiveButton( "confirmer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Paramterecl.this,"l'application est quitter",Toast.LENGTH_SHORT  ).show();
                      finish();
                    }
                } );
                builder.setNegativeButton( "annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Paramterecl.this,"annuler",Toast.LENGTH_SHORT  ).show();
                        dialogInterface.cancel();
                    }
                } );
                AlertDialog alert=builder.create();
                alert.show();


            }
        });
    }


}
