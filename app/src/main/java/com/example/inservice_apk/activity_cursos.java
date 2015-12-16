package com.example.inservice_apk;

import java.io.IOException;
import java.util.ArrayList;






import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;

import com.cuasmex.inservice_apk.R;


public class activity_cursos extends ListActivity {
	Intent myIntent;
	String nombre_area="", nombre_curso="";
	
   
	 ImageView slidingimage;
	 
	    
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	    
	    	super.onCreate(savedInstanceState);
			// Creamos la Base de datos
		    	crearBBDD();
			// Obtenemos la lista de Libros 
		    
		    	
	    	Bundle bundle = getIntent().getExtras();
	    	nombre_area= bundle.getString("nombre_area");
	    	
	    	
			ArrayList<Clase_Cursos> Cursos = getItems();
			setListAdapter(new LibroAdapter(this, R.layout.activity_cursos, Cursos));
		    getListView().setTextFilterEnabled(true);
		    getListView().setPadding(0, 150, 0, 0);
		    getListView().setBackgroundResource(R.drawable.fondo_cursos);
		    
		    //set listener onclick
		    getListView().setOnItemClickListener(  new OnCursosClick() ); 
		    
			
	    }   

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	       //   getMenuInflater().inflate(R.menu.activity_Cursos, menu);
	        return true;
	    }

	    private class OnCursosClick implements OnItemClickListener {

			public void onItemClick( AdapterView<?> arg0, View arg1, int arg2,long arg3)
			{
								
				LinearLayout linear = (LinearLayout) arg1 ;
				String curso = (String) ((TextView)linear.findViewById(R.id.nombre_curso2)).getText();
				//Toast.makeText(getApplicationContext(), "Clicked: " + country +" position "+arg2 , Toast.LENGTH_SHORT).show();
				
				Bundle bundle = new Bundle();
				bundle.putString("nombre_area", nombre_area);
				bundle.putString("nombre_curso", curso);
				
				// Creamos la vista de Lista de objetos y le pasamos la
				// colecci—n de objetos a mostrar
				myIntent = new Intent(activity_cursos.this, activity_tema.class);
				myIntent.putExtras(bundle);
				//startActivityForResult(myIntent, 0);
				startActivity(myIntent);
				
			 }
		  }
	    
	    private class LibroAdapter extends ArrayAdapter<Clase_Cursos> {

			private ArrayList<Clase_Cursos> items;

			public LibroAdapter(Context context, int textViewResourceId, ArrayList<Clase_Cursos> items) {
				super(context, textViewResourceId, items);
				this.items = items;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = convertView;
				if (v == null) {
					LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					v = vi.inflate(R.layout.activity_cursos, null);
				}
				Clase_Cursos Cursos = items.get(position);
				if (Cursos != null) {
					try {
						
					
					TextView titulo_curso = (TextView) v.findViewById(R.id.nombre_curso2);
					TextView titulo_area = (TextView) v.findViewById(R.id.nombre_area2);
					
				    if (titulo_curso != null) {
						titulo_curso.setText(Cursos.nombre_curso);
					}
					if (titulo_area != null) {
						titulo_area.setText(Cursos.nombre_area);
					}
					slidingimage = (ImageView) v.findViewById(R.id.iconos_cursos);
				   // slidingimage.setImageURI()
					Cursos.downloadFile(Cursos.imagen_curso, slidingimage);
				    
					} catch (Exception e) {
						Log.e("Error ", " Error al asiganar la imagen : " + Cursos.imagen_curso +" e: " + e.toString());
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
		public ArrayList<Clase_Cursos> getItems() {
			//Abrimos una conexi—n
			miBBDDHelper.abrirBaseDatos();
			//Consultamos los datos
			ArrayList<Clase_Cursos> listaCursos = miBBDDHelper.Getcursos(nombre_area);
			//Cerramos la conexion
			miBBDDHelper.close();
			//Devolvemos los datos
			return listaCursos;
		}

    
}
