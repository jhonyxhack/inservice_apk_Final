package com.example.inservice_apk;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.cuasmex.inservice_apk.R;


public class detalle_video extends Activity {
	String nombre_area="", nombre_curso="", nombre_tema="",video_url="";
	private static ProgressDialog progressDialog;
		private  MediaController mediaController;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_video);
        
        Bundle bundle = getIntent().getExtras();
    	nombre_area= bundle.getString("nombre_area");
    	nombre_curso = bundle.getString("nombre_curso");
    	nombre_tema = bundle.getString("nombre_tema");
    	video_url = bundle.getString("video_url");
    	
    	TextView view = (TextView) findViewById(R.id.textView1video);
    	view.setText(nombre_tema);//+ " curso : " + video_url);
        cargar_video();
    } 

   
    private void cargar_video()
    {
    	progressDialog = ProgressDialog.show(detalle_video.this, "", "Esta cargando el video ...", true);
        getWindow().setFormat(PixelFormat.TRANSLUCENT); 

        
         if(video_url.equals(""))
         {
        	 video_url="http://www.tech-inservice.com/mobile/videos/video1.3gp";
         }
         
            try {
                    final VideoView videoView = (VideoView) findViewById (R.id.videoView1);
                    mediaController = new MediaController(detalle_video.this);
                    mediaController.setAnchorView(videoView);
                    // Set video link (mp4 format )
                    Uri video = Uri.parse(video_url);
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(video);

                    videoView.setOnPreparedListener(new OnPreparedListener() {

                        public void onPrepared(MediaPlayer mp) {
                            progressDialog.dismiss();
                             videoView.start();
                        }
                    }); 

                 }catch(Exception e){
                      progressDialog.dismiss();
                     //System.out.println("Error al Reproducir el video del curso :"+e.getMessage());
                     Log.e("Error ", " Error al Reproducir el video del curso :" + e.toString());
                     abrir_regresar("", "", "", 0);
                 }

    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     super.onCreateOptionsMenu(menu);
     menu.add(0,0,0,"Home").setIcon(R.drawable.icono_areas);
     menu.add(0,1,0,"Areas").setIcon(R.drawable.icono_areas);
     menu.add(0,2,0,"Cursos").setIcon(R.drawable.icono_curso);
     menu.add(0,3,0,"Temas").setIcon(R.drawable.icono_tema);
     return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
     super.onOptionsItemSelected(item);
     //TextView view = (TextView) findViewById(R.id.textView1video);
     switch (item.getItemId()) {
     case 0:
           abrir_regresar("", "", "", 0);
      break;
     case 1:
    	   abrir_regresar("", "", "", 1);
      break;
     case 2:
    	   abrir_regresar(nombre_area, "", "", 2);
     break;
     
     case 3:
    	   abrir_regresar(nombre_area, nombre_curso, "", 3);
     break;
     
     default:
       //        view.setText("Debug.");
     break;
     }
     return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
     return super.onPrepareOptionsMenu(menu);
    }
    
    public void abrir_regresar ( String area, String curso, String tema, int key)
   	{
   		//String country = area
   		Intent myIntent;
   		Bundle bundle = new Bundle();
   		
   		switch (key) {
   		
   		    case 0:
		 	myIntent = new Intent(detalle_video.this, MainActivity.class);
	   		myIntent.putExtras(bundle);
	   		startActivity(myIntent);
            break;
            
		    case 1:
			myIntent = new Intent(detalle_video.this, activity_cursos.class);
	   		myIntent.putExtras(bundle);
	   		startActivity(myIntent);
            break;
       
		    case 2:
			bundle.putString("nombre_area",area);
	   		myIntent = new Intent(detalle_video.this, activity_cursos.class);
	   		myIntent.putExtras(bundle);
	   		startActivity(myIntent);
            break;
       
		    case 3:
			bundle.putString("nombre_area",area);
			bundle.putString("nombre_curso",curso);
	   		myIntent = new Intent(detalle_video.this, activity_tema.class);
	   		myIntent.putExtras(bundle);
	   		startActivity(myIntent);
            break;

	    default:
			
	    break;
			
		}
   		
   		
   	}
    
}
