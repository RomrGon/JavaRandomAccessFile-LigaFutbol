package ejercicios3FicherosDeAccesoAleatorio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class EjercicioRandomAccessFile2 {
	
	private static int longitudRegistros = 80 ; //Cantidad de Bytes que ocupa cada registro
	
	public static File fich = new File("liga.dat");
	

	public static void main(String[] args) {
				
		Scanner scnIn = new Scanner(System.in);

		String eleccion = "" ;
		
		do {
					
			System.out.println("\t\t\tMENÚ\n\n"
				+ "\ta)	Introducir datos\n"
				+ "\tb)	Mostrar todos los equipos\n"
				+ "\tc)	Buscar un equipo\n"
				+ "\td)	Borrar un equipo\n"
				+ "\te)	Abandonar el programa\n");
		
			System.out.print("Respuesta: ");

			eleccion = scnIn.nextLine();

			switch (eleccion) {
			case "a": {
				System.out.println("\tINTRODUCIR DATOS\n");

				introducirDatos();

				break;
			}
			case "b": {
				System.out.println("\tMostrar todos los Equipos");
				break;
			}
			case "c": {
				System.out.println("\tBuscar un Equipo");
				break;
			}
			case "d": {
				System.out.println("\tBorrar un Equipo");
				break;
			}
			case "e": {
				System.out.println("\tAbandonando el Programa");
				break;
			}
			default:
				System.out.println("Opción no válida");
			}

		} while (!eleccion.equals("e"));

		scnIn.close() ;

	}
	
	
//	/*
//	 * Método CrearFile recibe File como parámetro
//	 * En caso de que no exista el fichero lo crea.
//	 * Actualiza el número de la variable numeroRegistros.
//	 */
//	public static void crearFile() {
//		try(RandomAccessFile raf = new RandomAccessFile(fich, "rw")) {
//			
//			
//			
//		} catch (FileNotFoundException e) {
//			
//			e.printStackTrace();
//			
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
//	}
	
	/*
	 * Metodo introducirDatos
	 */
	public static void introducirDatos() {
		
		Scanner scnInt = new Scanner(System.in);
		Scanner scnChar = new Scanner(System.in);
		
		
		System.out.print("Nombre del Equipo: ");
		String nombre = scnChar.nextLine() ;
		
		System.out.print("Partidos Jugados: ");
		int partidosJugados = scnInt.nextInt() ;
		
		System.out.print("Partidos Ganados: ");
		int partidosGanados = scnInt.nextInt() ;
		
		System.out.print("Partidos Empatados: ");
		int partidosEmpatados = scnInt.nextInt() ;
		
		System.out.print("Partidos Perdidos: ");
		int partidosPerdidos = scnInt.nextInt() ;
		
		System.out.print("Puntos Acumulados en la Liga: ");
		int puntosAcumulados = scnInt.nextInt() ;
		

		try (RandomAccessFile raf = new RandomAccessFile(fich, "rw")) {
			
			raf.seek(raf.length());
			
			//Escritura del nombre en el fichero
			StringBuffer sbNombre = new StringBuffer(nombre) ;
			sbNombre.setLength(30) ;
			raf.writeChars(sbNombre.toString());
			
			//Escritura de los Int en el fichero
			raf.writeInt(partidosJugados);
			raf.writeInt(partidosGanados);
			raf.writeInt(partidosPerdidos);
			raf.writeInt(partidosEmpatados);
			raf.writeInt(puntosAcumulados);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	}
	
	
	public static void mostrarDatos() {
		
	}
	
	
	public static void buscarEquipo() {
		
	}
	
	
	public static void borrarEquipo() {
		
	}

}
