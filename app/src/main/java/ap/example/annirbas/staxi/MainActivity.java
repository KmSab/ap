package ap.example.annirbas.staxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {

private TextView entre,propos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_main);
        entre=(TextView)findViewById(R.id.tv2);
        propos=(TextView)findViewById(R.id.tv);
        entre.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                                         startActivity(intent);
                                     }
                                 }
        );
        propos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScrollingActivity2.class);
                startActivity(intent);
            }
        });

    }

}
