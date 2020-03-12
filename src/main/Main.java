package main;

import java.util.ArrayList;

import comm.ComunicacionTCP;
import processing.core.PApplet;

public class Main extends PApplet {

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	ComunicacionTCP comm;
	private String correo;
	private String clave;
	private String[] arregloRegistro;
	private ArrayList<Usuario> usuariosList;

	public void settings() {
		size(500, 500);
	}

	public void setup() {
		usuariosList = new ArrayList<Usuario>();
		comm = new ComunicacionTCP(this);
		comm.esperarConexion();

	}

	public void draw() {
		background(255);

	}

	public void mousePressed() {
		// comm.mandarMensaje("NO");
		System.out.println(usuariosList.size());
		for (int i = 0; i < usuariosList.size(); i++) {
			System.out.println(usuariosList.get(i).getCorreo());
		}

	}
	
	
	//Getters y setters

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String[] getArregloRegistro() {
		return arregloRegistro;
	}

	public void setArregloRegistro(String[] arregloRegistro) {
		this.arregloRegistro = arregloRegistro;
	}

	public ArrayList<Usuario> getUsuariosList() {
		return usuariosList;
	}

	public void setUsuariosList(ArrayList<Usuario> usuariosList) {
		this.usuariosList = usuariosList;
	}
	
	
}
