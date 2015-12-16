package com.example.inservice_apk;

import java.io.IOException;
import java.util.ArrayList;



import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;
import com.cuasmex.inservice_apk.R;

public class activity_tema extends ListActivity {
	Intent myIntent;
	String nombre_area="", nombre_curso="", video_url="";
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	      //    super.onCreate(savedInstanceState);  
	      //    setContentView(R.layout.activity_Cursos);
	    	super.onCreate(savedInstanceState);
			// Creamos la Base de datos
		    	crearBBDD();
			// Obtenemos la lista de Libros
		
	    	Bundle bundle = getIntent().getExtras();
	    	nombre_area= bundle.getString("nombre_area");
	    	nombre_curso = bundle.getString("nombre_curso");
	    	
			ArrayList<Clase_tema> Cursos = getItems();
			
			// Entregamos la lista de Libros al adaptador de la lista en el Layout
			// Lista.xml
			
			setListAdapter(new LibroAdapter(this, R.layout.activity_tema, Cursos));
			getListView().setTextFilterEnabled(true);
	        getListView().setPadding(0, 160, 0, 0);
	        getListView().setBackgroundResource(R.drawable.fondo_temas);
		       //set listener onclick
	        
		    getListView().setOnItemClickListener(new OnCursosClick() );
	    }   

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	      //  getMenuInflater().inflate(R.menu.activity_Cursos, menu);
	        return true;
	    }

	    private class OnCursosClick implements OnItemClickListener {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
			{
				//TextView text_click = (TextView) arg1 ;
				//Toast.makeText(getApplicationContext(), "Clicked: " + text_click.getText() +" position "+arg2 , Toast.LENGTH_SHORT).show();
				
				LinearLayout linear = (LinearLayout) arg1 ;
				String tema = (String) ((TextView)linear.findViewById(R.id.nombre_curso3)).getText();
				 video_url = (String) ((TextView)linear.findViewById(R.id.nombre_area3)).getText();
				//Toast.makeText(getApplicationContext(), "Clicked: " + tema +" position "+arg2 , Toast.LENGTH_SHORT).show();
				
				Bundle bundle = new Bundle();
				bundle.putString("nombre_tema",tema);
				bundle.putString("nombre_curso",nombre_curso);
				bundle.putString("nombre_area",nombre_area);
				bundle.putString("video_url",video_url );
				
		    	myIntent = new Intent(activity_tema.this, detalle_video.class);
				myIntent.putExtras(bundle);
			    startActivity(myIntent);
				
			 }
		      }
	    
	    private class LibroAdapter extends ArrayAdapter<Clase_tema> {

			private ArrayList<Clase_tema> items;

			public LibroAdapter(Context context, int textViewResourceId,
					ArrayList<Clase_tema> items) {
				super(context, textViewResourceId, items);
				this.items = items;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = convertView;
				if (v == null) {
					LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					v = vi.inflate(R.layout.activity_tema, null);
				}
				Clase_tema Cursos = items.get(position);
				if (Cursos != null) {
					TextView ttitulo = (TextView) v.findViewById(R.id.nombre_curso3);
					TextView tautor = (TextView) v.findViewById(R.id.nombre_area3);
					if (ttitulo != null) {
						ttitulo.setText(Cursos.nombre_tema);
					}
					if (tautor != null) {
						tautor.setText(Cursos.video_url);
					}  
				}
				return v;
			}
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

		/** Called when the activity is first created. */
		

		/*
		 * Obtiene una lista de libros
		 * 
		 * @returns ArrayList<Libro> libros
		 */
		public ArrayList<Clase_tema> getItems() {
			//Abrimos una conexi—n
			miBBDDHelper.abrirBaseDatos();
			//Consultamos los datos
			ArrayList<Clase_tema> listatemas = miBBDDHelper.Gettemas(nombre_area, nombre_curso);
			//Cerramos la conexion
			miBBDDHelper.close();
			//Devolvemos los datos
			return listatemas;
		}

    
}
