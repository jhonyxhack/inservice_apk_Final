package com.example.inservice_apk;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Clase_Cursos {
	public String nombre_area = "";
	public String fecha_inicio = "";
	public String nombre_curso="";
	public String imagen_curso="";
	public String url_video_curso="";
	/*
	public String getTitulo() {
		return nombre_area;
	}

	public String getAutor() {
		return fecha_inicio;
	}

	public void setTitulo(String titulo) {
		nombre_area = titulo;
	}

	public void setAutor(String autor) {
		fecha_inicio = autor;
	} */
	private Bitmap loadedImage;
	
	 public void downloadFile(String imageHttpAddress,ImageView  imageView ) {
	        URL imageUrl = null;
	        try {
	            imageUrl = new URL(imageHttpAddress);
	            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
	            conn.connect();
	            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
	            imageView.setImageBitmap(loadedImage);
	        } catch (IOException e) {
	           // Toast.makeText(getApplicationContext(), "Error cargando la imagen: "+e.getMessage(), Toast.LENGTH_LONG).show();
	            e.printStackTrace();
	        }
	    }
	 
	 public void downloadFile_Button(String imageHttpAddress,ImageButton  imageView ) {
	        URL imageUrl = null;
	        try {
	            imageUrl = new URL(imageHttpAddress);
	            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
	            conn.connect();
	            loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
	            imageView.setImageBitmap(loadedImage);
	            conn.disconnect();
	             } catch (IOException e) {
	            Log.e("Error ", " Error al descargar las imagenes de los cursos Gratuitos " + e.toString());
	        }
	    }
	 
	 public String limpiar_url (String url_video)
     {
    	 //http://www.tech-inservice.com/files/videos_cursos/sesion1-58-2.mp4
		 try {

			 String videos[] = new String[3];
			 videos = url_video.split(";");
			 
	    	 url_video = videos[0]; //url_video.replace(";", "");
	    	 url_video = "http://www.tech-inservice.com/files/videos_cursos/" + url_video;
	    	  		
	    	    	 
		} catch (Exception e) {
			// TODO: handle exception
			url_video= "";
		}
		 Log.d("Debug", " Url del video -> " + url_video.toString());
		 return url_video;
     }
}

