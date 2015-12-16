package com.example.inservice_apk;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDatosHelper extends SQLiteOpenHelper {
		 
	    //La carpeta por defecto donde Android espera encontrar la Base de Datos de tu aplicaci�n
	    private static String DB_PATH = "/data/data/com.cuasmex.inservice_apk/databases/";
	// private static String DB_PATH = "/data/";
	    private static String DB_NAME = "bd_tech.db";
	    private SQLiteDatabase myDataBase;  
	 
	    private final Context myContext;
	 
	    /*
	     * Constructor
	     *   
	     * Guarda una referencia al contexto para acceder a la carpeta assets de la aplicaci�n y a los recursos
	     * @param contexto
	     */
	    public BaseDatosHelper(Context contexto) {
	 
	    	super(contexto, DB_NAME, null, 1);
	        this.myContext = contexto;
	    }	
	 
	  /* 
	   * Crea una base de datos vac�a en el sistema y la sobreescribe con la que hemos puesto en Assets
	   */
	    public void crearDataBase() throws IOException{
	 
	    	boolean dbExist = comprobarBaseDatos();
	 
	    	if(dbExist){
	    		// Si ya existe no hacemos nada
	    		
	    	}else{
	    		//Si no existe, creamos una nueva Base de datos en la carpeta por defecto de nuestra aplicaci�n, 
	    		//de esta forma el Sistema nos permitir� sobreescribirla con la que tenemos en la carpeta Assets
	        	this.getReadableDatabase();
	        	try {
	    			copiarBaseDatos();
	    		} catch (IOException e) {
	        		//throw new Error("Error al copiar la Base de Datos");
	        		Log.e("Error ", " Error al copiar base de Datos funcion crearDataBase-> " + e.toString());
	        	}
	    	}
	    }
	 
	    /*
	     * Comprobamos si la base de datos existe
	     * @return true si existe, false en otro caso
	     */
	    private boolean comprobarBaseDatos(){
	    	SQLiteDatabase checkDB = null;
	    	try{
	    		String myPath = DB_PATH + DB_NAME;
	    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
//SQLiteDatabase.OPEN_READONLY );
	    	}catch(SQLiteException e){
	    		//No existe
	    		Log.e("Error ", " Error al copiar base de Datos funcion comprobarBaseDatos-> " + e.toString());
	    	}
	 
	    	if(checkDB != null){
	    		checkDB.close();
	    	}
	 
	    	return checkDB != null ? true : false;
	    }
	 
	    /*
	     * Copia la base de datos desde la carpeta Assets sobre la base de datos vac�a reci�n creada en la carpeta del sistema,
	     * desde donde es accesible
	     */
	    private void copiarBaseDatos() throws IOException{
	 
	    	try
	    	{
	    	//Abrimos la BBDD de la carpeta Assets como un InputStream
	    	InputStream myInput = myContext.getAssets().open(DB_NAME);
	    	//Carpeta de destino (donde hemos creado la BBDD vacia)
	    	String outFileName = DB_PATH + DB_NAME;
	 
	    	//Abrimos la BBDD vacia como OutputStream
	    	OutputStream myOutput = new FileOutputStream(outFileName);
	 
	    	//Transfiere los Bytes entre el Stream de entrada y el de Salida
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer))>0){
	    		myOutput.write(buffer, 0, length);
	    	}
	 
	    	//Cerramos los ficheros abiertos
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	    	}
	    	catch(Exception e){
	    		//No existe
	    		Log.e("Error ", " Error al copiar base de Datos funcion copiarBaseDatos-> " + e.toString());
	    	}
	    }
	 
	    /*
	     * Abre la base de datos
	     */
	    public void abrirBaseDatos() throws SQLException{
	    	try
	    	{
	        String myPath = DB_PATH + DB_NAME;
	    	myDataBase = SQLiteDatabase.openDatabase(myPath, null,  SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);//SQLiteDatabase.OPEN_READONLY );
	    	}
	    	catch(Exception e){
	    		//No existe
	    		Log.e("Error ", " Error al copiar base de Datos funcion abrirBaseDatos-> " + e.toString());
	    		
	    	}
	    	
	 
	    }
	 
	    /*
	     * Cierra la base de datos
	     */
	    @Override
		public synchronized void close() {
	    	    if(myDataBase != null)
	    		    myDataBase.close();
	 
	    	    super.close();
		}
	 
	    
		@Override
		public void onCreate(SQLiteDatabase db) {
			//No usamos este m�todo
		}
	 
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			//No usamos este m�todo
		}
	 
		//Podemos a�adir m�todos p�blicos que accedan al contenido de la base de datos, 
		//para realizar consultas, u operaciones CRUD (create, read, update, delete)

	/*	private final String TABLE_areas = "tabla_areas";
		private final String TABLE_KEY_ID = "id_area";
		private final String TABLE_KEY_TITULO = "nombre";
		private final String TABLE_KEY_AUTOR = "activo";
		*/
		/*
	     * Obtiene todos los libros desde la Base de Datos
	     */
	     public ArrayList<Clase_area> GetLibros(){
	     	ArrayList<Clase_area> listaAreas = new ArrayList<Clase_area>();
	     	
	     	//Cursor c = myDataBase.query(TABLE_areas,	new String[] {TABLE_KEY_ID, TABLE_KEY_TITULO, TABLE_KEY_AUTOR},    			null, null, null, null, null);
	     	String sql = "select nombre from tabla_areas ";
	     	Cursor c = myDataBase.rawQuery(sql, null);
	     	
	     	//Iteramos a traves de los registros del cursor
	     	c.moveToFirst();
	         while (c.isAfterLast() == false) {
	         	Clase_area area = new Clase_area();
	         	area.setTitulo(c.getString(0));
	         	//area.setAutor(c.getString(2));
	         	listaAreas.add(area);
	        	    c.moveToNext();
	         }
	         c.close();
	         
	         return listaAreas;
	     }

	ObtenerWebService hiloconexion;
	     public ArrayList<Clase_Cursos> Getcursos(String nombre_area){
		     	ArrayList<Clase_Cursos> listacursos = new ArrayList<Clase_Cursos>();

			 hiloconexion = new ObtenerWebService();
			 hiloconexion.execute(hiloconexion.UrlGetAreas,"1");   // Parámetros que recibe doInBackground



		     	//Cursor c = myDataBase.query(TABLE_areas,	new String[] {TABLE_KEY_ID, TABLE_KEY_TITULO, TABLE_KEY_AUTOR},	     			null, null, null, null, null);
		     	String sql = "select nombre_curso,nombre_area,extra3 from tabla_cursos where nombre_area='NuevosCursos' and activo='Nuevos' order by id_curso desc ";
		     	Cursor c = myDataBase.rawQuery(sql, null);
		     	
		     	//Iteramos a traves de los registros del cursor
		     	c.moveToFirst();
		         while (c.isAfterLast() == false) {
		         	Clase_Cursos curso = new Clase_Cursos();
		         	curso.nombre_area = (c.getString(1));
		         	curso.nombre_curso = (c.getString(0));
		         	curso.imagen_curso = "http://www.tech-inservice.com/files/videos_cursos/chicas/" + c.getString(2) ;
		         	//curso.url_video_curso = "http://www.tech-inservice.com/files/videos_cursos/chicas/" + c.getString(2) ;
		         	listacursos.add(curso);
		             c.moveToNext();
		         }
		         c.close();
		         
		         return listacursos;
		     }
	     
	     public Clase_Cursos[] Getcursos_gratis(){
		     	
	    	 String sql = "select nombre_curso,nombre_area,extra3, foto_chica,video_introduccion from tabla_cursos where  foto_chica != '' order by id_curso asc limit 5 ";
		     	Cursor c = myDataBase.rawQuery(sql, null);
		     	Integer x=0;
		     	Clase_Cursos curso[] = new Clase_Cursos[5];
		     	//Iteramos a traves de los registros del cursor
		     	c.moveToFirst();
		         while (c.isAfterLast() == false) {
		         	 curso[x] = new Clase_Cursos();
		         	curso[x].nombre_area = (c.getString(1));
		         	curso[x].nombre_curso = (c.getString(0));
		         	curso[x].imagen_curso = "http://www.tech-inservice.com/files/videos_cursos/" + c.getString(3) ;
		         	curso[x].url_video_curso = curso[x].limpiar_url(c.getString(4));
		             c.moveToNext();
		             x++;
		         }
		         c.close();
		         
		        return curso;
		     }
	     
	     Clase_Cursos curso = new Clase_Cursos();
	     public ArrayList<Clase_tema> Gettemas(String nombre_area,String nombre_curso ){
		     	ArrayList<Clase_tema> listatemas = new ArrayList<Clase_tema>();
		     	
		     	
		     	String sql = "select nombre_area,nombre_curso,nombre_tema,descripcion,autor,archivo from tabla_temas where nombre_curso='" + nombre_curso +"'";
		     	Cursor c = myDataBase.rawQuery(sql, null);
		     	
		     	//Iteramos a traves de los registros del cursor
		     	c.moveToFirst();
		         while (c.isAfterLast() == false) {
		         	Clase_tema tema = new Clase_tema();
		         	tema.nombre_area = (c.getString(0));
		         	tema.nombre_curso = (c.getString(1));
		         	tema.nombre_tema = (c.getString(2));
		         	tema.descripcion_tema = (c.getString(3));
		         	tema.autor_tema = (c.getString(4));
		         	tema.video_url = curso.limpiar_url(c.getString(5));
		         	listatemas.add(tema);
		             c.moveToNext();
		         }
		         c.close();
		         
		         return listatemas;
		     }
	    
}
