package ap.example.annirbas.staxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class annuler extends Activity {
String idobesrve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_annuler);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        idobesrve=(String) b.get("id");
        Toast.makeText(annuler.this,idobesrve,Toast.LENGTH_LONG).show();

     /*   try {
            URL url=new URL("www.fb.com" ) ;

            String [] l={};

for (int i=0;i<=l.length;i++)

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }

*/
    }
}
