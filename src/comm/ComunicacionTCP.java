package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import main.Main;
import main.Usuario;

public class ComunicacionTCP extends Thread {
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	private String line;

	private Main main;

	public ComunicacionTCP(Main main) {
		this.main = main;
	}

	// Hilo de recepcion
	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(5000);
			System.out.println("Esperando...");
			this.socket = server.accept();
			System.out.println("Conexión aceptada");

			// Reader
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			this.reader = new BufferedReader(isr);

			// Writer
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			this.writer = new BufferedWriter(osw);

			while (true) {
				recibirMensaje();

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Esperar conexion
	public void esperarConexion() {
		this.start();
	}

	// Mandar un mensaje
	public void mandarMensaje(String mensaje) {
		new Thread(

				() -> {
					try {
						writer.write(mensaje + "\n");
						writer.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

		).start();

	}

	// Recibir mensaje
	public void recibirMensaje() throws IOException {
		line = reader.readLine();
		// System.out.println(line);

		main.setArregloRegistro(line.split(","));
		main.setCorreo(main.getArregloRegistro()[0]);
		main.setClave(main.getArregloRegistro()[1]);

		String correo = main.getArregloRegistro()[0];
		String clave = main.getArregloRegistro()[1];

		//Validar correo repetido
		boolean correoRepetido = false;
		
		for (int i = 0; i < main.getUsuariosList().size(); i++) {
			if ( correo.equals(main.getUsuariosList().get(i).getCorreo()) ) {
				correoRepetido = true;
				break;
			}
		}
		
		if(correoRepetido) {
			//Mandar mensaje a Android
			mandarMensaje("REPETIDO");
			
		} else {
			main.getUsuariosList().add(new Usuario(correo, clave));
			mandarMensaje("OK");
			
		}

	}

	// Cerrar conexion
	public void cerrarConexion() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	//Getters y setters

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

}
