package spic.nau_btl;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static spic.nau_btl.TirarFotoActivity.globalstringname;

/**
 * Created by Eder Barbosa on 08/03/2017.
 */
public class MostraFoto extends AppCompatActivity {
          private ImageView mImageView;
          private RadioButton radioButtonTermos;
          private   Bitmap bitmap;
          private static Button button_sbm;
          private RadioGroup radioGroup;
          private EditText editTextEmail;



          private static Button guardarFoto;
          private static Button return_clik;
          private static String nomeFoto;

        private RelativeLayout loading;

          private static final int request_code=0;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_foto);

        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(uiOptions);

        Typeface grupe=Typeface.createFromAsset(getAssets(),"fonts/MyriadPro-Regular.otf");
        TextView termosTest=(TextView) findViewById(R.id.textViewTermos);
        TextView termosTest2=(TextView) findViewById(R.id.textViewTermos2);
        TextView termosConcordo=(TextView) findViewById(R.id.textViewConcordo);

        termosTest.setTypeface(grupe);
        termosTest2.setTypeface(grupe);

        termosConcordo.setTypeface(grupe);

        loading = (RelativeLayout) findViewById(R.id.loading_holder);

        return_clik=(Button)findViewById(R.id.buttonTirarNovaFoto);
        return_clik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MostraFoto.this, TirarFotoActivity.class);
                nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(nextActivity);
                finish();
            }
        });

        mostrarImagem();

        guardarFoto =(Button)findViewById(R.id.guardarFoto);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);





        guardarFoto.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {


                send_foto();
            }
        });



        editTextEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    View decorView = getWindow().getDecorView();
                    // Hide both the navigation bar and the status bar.
                    // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
                    // a general rule, you should design your app to hide the status bar whenever you
                    // hide the navigation bar.
                    int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

                    decorView.setSystemUiVisibility(uiOptions);
                }
            }
        });

        editTextEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    send_foto();

                    handled = true;
                }

                return handled;
            }
        });


    }
    /*

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();


        return activeNetworkInfo != null;
    }
*/

    private void send_foto(){
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE

        );
        imm.hideSoftInputFromWindow(editTextEmail.getWindowToken(), 0);



        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        int radioSelect = radioGroup.getCheckedRadioButtonId();
        String emailEnviar = editTextEmail.getText().toString();


        if(radioSelect!=-1 && !TextUtils.isEmpty(emailEnviar)) {
            loading.setVisibility(View.VISIBLE);
            network();

        }else if(radioSelect==-1){
            Toast.makeText(MostraFoto.this, "Aceite os termos acima referidos", Toast.LENGTH_LONG).show();

        }else if(TextUtils.isEmpty(emailEnviar)){
            Toast.makeText(MostraFoto.this, "Prencha o campo do email", Toast.LENGTH_LONG).show();

        }



        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(uiOptions);
    }

    public void network( ){

      // if (isNetworkAvailable()) {
            RetrieveFeedTask task = new RetrieveFeedTask();
            task.execute();
        /*
        } else {

        }


/*
        if (isNetworkAvailable()) {


        } else {

            Toast.makeText(MostraFoto.this, "Ligue o Wireless ou os dados móveis", Toast.LENGTH_LONG).show();
        }
        */
    }












    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {


        }

        protected String doInBackground(Void... urls) {

            // Do some validation here

            try {
                Log.e("dwq","asdcwa");
                URL url = new URL("http://terracottasunset.com/fotografias/save_photo.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("image", encodedImage)
                        .appendQueryParameter("imagename", globalstringname);
                String query = builder.build().getEncodedQuery();


                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {


            if (response == null) {
                response = "THERE WAS AN ERROR";

                Toast.makeText(MostraFoto.this, "Ocurreu um erro ao enviar a foto", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(MostraFoto.this, "A foto foi enviada com sucesso", Toast.LENGTH_LONG).show();
                finish();


            }
            Log.i("INFO", response);
        }


    }
    public void mostrarImagem(){
        nomeFoto = getIntent().getStringExtra("EXTRA_SESSION_ID");

        File imageFile = new File("/sdcard/DCIM/NAU/"+nomeFoto);
        ImageView jpgView = (ImageView)findViewById(R.id.imageViewImagem);

         bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        jpgView.setImageBitmap(bitmap);




    }




    @Override
    protected void onResume() {
        super.onResume();

        editTextEmail.clearFocus();
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(uiOptions);

    }


}