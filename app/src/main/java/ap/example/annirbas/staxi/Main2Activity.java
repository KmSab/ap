package ap.example.annirbas.staxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Main2Activity extends Activity{

    private TextView cl,tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        cl=(TextView)findViewById(R.id.tv);
        tax=(TextView)findViewById(R.id.tv2);
        cl.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         Intent intent = new Intent(Main2Activity.this, LoginActivitycl.class);
                                         startActivity(intent);
                                     }
                                 }
        );
        tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, LoginActivitytax.class);
                startActivity(intent);
            }
        });

    }
    }



