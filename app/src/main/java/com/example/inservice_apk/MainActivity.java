package com.example.inservice_apk;

import java.io.IOException;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.cuasmex.inservice_apk.R;

public class MainActivity extends Activity implements OnClickListener {
	final public static String MyKey = "mikey";
	String video_url = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		crearBBDD();
		// ImageButton next = (ImageButton) findViewById(R.id.imageButton1);

		View boton_menu = findViewById(R.id.button1);
		boton_menu.setOnClickListener(this);
		
		View boton1 = findViewById(R.id.imagen1);
		boton1.setOnClickListener(this);

		View boton2 = findViewById(R.id.imagen2);
		boton2.setOnClickListener(this);
		
		View boton3 = findViewById(R.id.imagen3);
		boton3.setOnClickListener(this);
		
		/*View boton4 = findViewById(R.id.imagen4);
		boton4.setOnClickListener(this);

		View boton5 = findViewById(R.id.imagen5);
		boton5.setOnClickListener(this);
		*/
		getCursos_gratis();

	}
	BaseDatosHelper miBBDDHelper;
	public void crearBBDD() {
	     miBBDDHelper = new BaseDatosHelper(this);
	try {
		   miBBDDHelper.crearDataBase();
	} catch (IOException ioe) {
		throw new Error("Unable to create database");
	}
}
	Clase_Cursos curso[] = new Clase_Cursos[5];
	
	public void getCursos_gratis() {
		//Abrimos una conexi—n
		try {
        miBBDDHelper.abrirBaseDatos();
		curso = miBBDDHelper.Getcursos_gratis();
		//Cerramos la conexion
		miBBDDHelper.close();
		//Devolvemos los datos
		
		 
		ImageButton img0 = (ImageButton) findViewById(R.id.imagen1);
		 curso[0].downloadFile_Button(curso[0].imagen_curso, img0);
		
		 ImageButton img1 = (ImageButton) findViewById(R.id.imagen2);
		 curso[1].downloadFile_Button(curso[1].imagen_curso, img1);
		
		 ImageButton img2 = (ImageButton) findViewById(R.id.imagen3);
		 curso[2].downloadFile_Button(curso[2].imagen_curso, img2);
		 /*
		 ImageButton img3 = (ImageButton) findViewById(R.id.imagen4);
		 curso[3].downloadFile_Button(curso[3].imagen_curso, img3);
		
		
		 ImageButton img4 = (ImageButton) findViewById(R.id.imagen5);
		 curso[4].downloadFile_Button(curso[4].imagen_curso, img4);
		 */
		 		
		} catch (Exception e) {
			Log.e("Error ", " Error al abrir las imagenes de cursos gratuitos " + e.toString());
		}
		
	}
	
	public void onClick(View v) {

		try {
			 if (v.getId() == findViewById(R.id.imagen1).getId()) {
					abrir_video(curso[0].url_video_curso,curso[0].nombre_area,curso[0].nombre_curso,"");
				} else if (v.getId() == findViewById(R.id.imagen2).getId()) {
					abrir_video(curso[1].url_video_curso,curso[1].nombre_area,curso[1].nombre_curso,"");
				} else if (v.getId() == findViewById(R.id.imagen3).getId()) {
					abrir_video(curso[2].url_video_curso,curso[2].nombre_area,curso[2].nombre_curso,"");
				/*} else if (v.getId() == findViewById(R.id.imagen4).getId()) {
					abrir_video(curso[3].url_video_curso,curso[3].nombre_area,curso[3].nombre_curso,"");
				} else if (v.getId() == findViewById(R.id.imagen5).getId()) {
					abrir_video(curso[4].url_video_curso,curso[4].nombre_area,curso[4].nombre_curso,"");*/
				} 
				  else if (v.getId() == findViewById(R.id.button1).getId()) {
					Bundle bundle = new Bundle();
					bundle.putString("nombre_area","NuevosCursos");
					bundle.putString(MyKey,"Esto lo hemos enviado desde la vista principal.");
					Intent myIntent = new Intent(MainActivity.this,	activity_cursos.class);
					myIntent.putExtras(bundle);
					startActivity(myIntent);
				}  
		} catch (Exception e) {
			Log.e("Error", " Error al abrir los botones de los cursos Gratis " + e.toString());
		}
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, "Pagina Oficial").setIcon(R.drawable.ic_launcher);
		// menu.add(0,1,0,"Areas").setIcon(R.drawable.icono_areas);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		// TextView view = (TextView) findViewById(R.id.textView1video);
		switch (item.getItemId()) {

		case 0:
			Intent viewIntent = new Intent("android.intent.action.VIEW",
					Uri.parse("http://www.tech-inservice.com"));
			startActivity(viewIntent);
			break;

		case 1:

			break;

		default:

			break;

		}
		return false;
	}

	public void abrir_video(String video,String name_area,String name_curso,String name_tema) {
		// String country = area
		Intent myIntent;
		Bundle bundle = new Bundle();
		bundle.putString("video_url", video);
	 bundle.putString("nombre_area", name_area);
		bundle.putString("nombre_curso", name_curso);
		bundle.putString("nombre_tema", name_tema);
		myIntent = new Intent(MainActivity.this, detalle_video.class);
		myIntent.putExtras(bundle);
		startActivity(myIntent);
	}

}
