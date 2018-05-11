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

public class ParamtereTax extends Activity {
    private TextView btn1,btn2,btn3;
private String idobes,pswbserve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_paramtere_tax );
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        idobes=(String) b.get("cod_tax");
        pswbserve=(String) b.get("psw");
        iin.putExtra("cod_tax",idobes);
        iin.putExtra("psw",pswbserve);
        btn1=(TextView)findViewById( R.id.btn1 );
        btn2=(TextView)findViewById( R.id.btn2);
        btn3=(TextView)findViewById( R.id.btn3 );
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent neo = new Intent(ParamtereTax.this, Motpassetaxmodifier.class);
                neo.putExtra("psw",pswbserve);
                neo.putExtra("cod_tax",idobes);
                startActivity(neo);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent neo = new Intent(ParamtereTax.this, FAQ.class);
                startActivity(neo);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder( ParamtereTax.this );
                builder.setTitle( "Quit !" );
                builder.setMessage( "Voulez-vous quitter l'application ?" );
                // builder.setIcon( R.drawable.icon );
                builder.setCancelable( true );


                builder.setPositiveButton( "confirmer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ParamtereTax.this,"l'application est quitter",Toast.LENGTH_SHORT  ).show();
                        finish();
                    }
                } );
                builder.setNegativeButton( "annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ParamtereTax.this,"annuler",Toast.LENGTH_SHORT  ).show();
                        dialogInterface.cancel();
                    }
                } );
                AlertDialog alert=builder.create();
                alert.show();


            }
        });

            }

    }

