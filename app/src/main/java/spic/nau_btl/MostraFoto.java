package spic.nau_btl;



        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.drawable.BitmapDrawable;
        import android.net.Uri;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.util.SparseIntArray;

        import android.view.Surface;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;

        import java.io.File;

/**
 * Created by Eder Barbosa on 08/03/2017.
 */
public class MostraFoto extends AppCompatActivity {
          private ImageView mImageView;

          private static Button return_clik;
          private static String nomeFoto;
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

        voltarParaTras();
        mostrarImagem();




    }
    public void mostrarImagem(){
        nomeFoto = getIntent().getStringExtra("EXTRA_SESSION_ID");




// File  imagemGet= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);


        File imageFile = new File("/sdcard/DCIM/SunsetTerracotta/"+nomeFoto);
        ImageView jpgView = (ImageView)findViewById(R.id.imageViewImagem);

        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        jpgView.setImageBitmap(bitmap);







    }


    public void voltarParaTras(){

        return_clik=(Button)findViewById(R.id.buttonTirarNovaFoto);
        return_clik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(MostraFoto.this, TirarFotoActivity.class);
                startActivity(nextActivity);


            }
        });



    }


}