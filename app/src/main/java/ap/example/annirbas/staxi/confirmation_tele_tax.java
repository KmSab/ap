package ap.example.annirbas.staxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class confirmation_tele_tax extends Activity{
    private static final String TAG="PhoneAuth";
     FirebaseAuth auth;
     EditText e1,e2;
     Button veributton,sendbutton,resendbutton,singoutbutton;
     PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
     String  phoneVerificationId;
     private PhoneAuthProvider.ForceResendingToken resendtoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView(R.layout.activity_confirmation_tele_tax);
        e1=(EditText)findViewById(R.id.etnum);
        e2=(EditText)findViewById(R.id.etcode);
        veributton=(Button)findViewById(R.id.verify);
        sendbutton=(Button) findViewById(R.id.envoi);
        resendbutton=(Button)findViewById(R.id.renvoyer);
        singoutbutton=(Button)findViewById(R.id.singout);
        veributton.setEnabled(false);
        resendbutton.setEnabled(false);
        singoutbutton.setEnabled(false);
        singoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSecondActivity();
            }
        });
        auth=FirebaseAuth.getInstance();
    }
    private void goToSecondActivity()
    {
        Intent intent = new Intent(this, fortaxins.class);
        Toast.makeText(confirmation_tele_tax.this,"entrez tes informations",Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
    public void send_sms(View v){
     String number=e1.getText().toString();
     setUpVerificationCallBacks();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,60, TimeUnit.SECONDS,this,verificationCallbacks

        );
    }
    private void setUpVerificationCallBacks() {
      verificationCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
          @Override
          public void onVerificationCompleted(PhoneAuthCredential Credential) {
          singoutbutton.setEnabled(true);
          resendbutton.setEnabled(false);
          veributton.setEnabled(false);
          e2.setText("");
              singInWithPhoneAuthCredential( Credential);
          }
          @Override
          public void onVerificationFailed(FirebaseException e) {
              if (e instanceof FirebaseAuthInvalidCredentialsException){
                  Log.d(TAG,"invalid credential:"+e.getLocalizedMessage());
              }else if (e instanceof FirebaseTooManyRequestsException){
                  Log.d(TAG,"sms quota exceded");
              }
          }
          public void onCodeSent(String verficationcode ,PhoneAuthProvider.ForceResendingToken token){
              phoneVerificationId=verficationcode;
              resendtoken=token;
              veributton.setEnabled(true);
              sendbutton.setEnabled(false);
              resendbutton.setEnabled(true);
          }
      };
    }
   public void verifyCode(View v){
       String code = e2.getText().toString();
   PhoneAuthCredential credential=
           PhoneAuthProvider.getCredential( phoneVerificationId,code);
   singInWithPhoneAuthCredential(credential);
   }
     public void  singInWithPhoneAuthCredential(PhoneAuthCredential credential){
    auth.signInWithCredential(credential)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
      @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()){
               singoutbutton.setEnabled(true);
               e2.setText("");
               resendbutton.setEnabled(false);
               veributton.setEnabled(false);
               FirebaseUser user=task.getResult().getUser();
             }else {
           if (task.getException()instanceof
                   FirebaseAuthInvalidCredentialsException){
           }}
      } });
    }
public void resendcode(View v){
         String phoneNumber=e1.getText().toString();
         setUpVerificationCallBacks();
         PhoneAuthProvider.getInstance().verifyPhoneNumber(
                 phoneNumber,60,TimeUnit.SECONDS,this,verificationCallbacks,resendtoken
         );
}
public void singOut(View v){
    auth.signOut();
    singoutbutton.setEnabled(false);
    sendbutton.setEnabled(true);
}
}
