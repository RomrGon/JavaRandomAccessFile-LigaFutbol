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
				
				mostrarDatos() ;
				
				break;
			}
			case "c": {
				System.out.println("\tBuscar un Equipo");
				
				if (!buscarEquipo()) {
					System.out.println("No se ha encontrado ningún equipo\n");
				}
				
				break;
			}
			case "d": {
				System.out.println("\tBorrar un Equipo");
				
				borrarEquipo() ;
				
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
	
		
	/*
	 * Metodo introducirDatos
	 * Agregará un registro nuevo al fichero con los datos indicados por el usuario
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
	
	/*
	 * Método mostrarDatos
	 * Mostrará en el terminal los registros del fichero
	 * No mostrará los registros marcados para eliminar (primer byte 0)
	 */
	public static void mostrarDatos() {
		
		int cantidadRegistros = (int)fich.length() / longitudRegistros ;
		char caracterComparacion = '0' ;
		boolean continuar = true ;
		
		System.out.println("\t\tLIGA");
		System.out.println("Nombre del Equipo"
				+ "\t\tPlays"
				+ "\tWins"
				+ "\tTies"
				+ "\tLost"
				+ "\tTotal Points");
		
		try (RandomAccessFile raf = new RandomAccessFile(fich, "r")) {
						
			for (int i = 0 ; i <cantidadRegistros ; i ++) {
				
				raf.seek(i * longitudRegistros);
				
				//Lectura del nombre (String)
				for (int j = 0 ; j < 30 ; j ++) {	
					
					if (j == 0) {
						caracterComparacion = raf.readChar() ;
//						System.out.println((byte)caracterComparacion);
						raf.seek(i * longitudRegistros);
					}
					
					if ((byte)caracterComparacion == 0) {
						continuar = false ;
						
					} else {
						continuar = true ;
						System.out.print(raf.readChar());						
					}
					
				}
				
				if (continuar) {
					//Lectura de los Números (int)
					System.out.print("\t" + raf.readInt());
					System.out.print("\t" + raf.readInt());
					System.out.print("\t" + raf.readInt());
					System.out.print("\t" + raf.readInt());
					System.out.print("\t\t" + raf.readInt() + "\n");					
				}
				
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		System.out.println();
	}
	
	/*
	 * Método buscarEquipo
	 * Mostrará todos los datos del registro cuya columna "nombre" coincida con el indicado por el usuario
	 * No mostrará los registros marcados para eliminar (primer byte 0)
	 */
	public static boolean buscarEquipo() {
		Scanner scnStr = new Scanner(System.in) ;
		int cantidadRegistros = (int)fich.length() / longitudRegistros ;
		String nombreEncontrado = "" ;
		
		System.out.print("Introduzca el nombre del equipo: ");
		String nombreBuscado = scnStr.nextLine().toLowerCase() ;
		
		if (nombreBuscado.equals("")) {
			return false ;
			
		} else {
			try (RandomAccessFile raf = new RandomAccessFile(fich, "r")) {
				
				for (int i = 0 ; i <cantidadRegistros ; i ++) {
					
					raf.seek(i * longitudRegistros);
					
					//Lectura del nombre (String)
					for (int j = 0 ; j < 30 ; j ++) {
						
						nombreEncontrado = nombreEncontrado + raf.readChar() ;
					}
					
					if (nombreEncontrado.toLowerCase().trim().equals(nombreBuscado)) {
						
						System.out.println("\nNombre del Equipo"
								+ "\t\tPlays"
								+ "\tWins"
								+ "\tTies"
								+ "\tLost"
								+ "\tTotal Points");
						
						System.out.print(nombreEncontrado);
						
						//Lectura de los Números (int)
						System.out.print("\t" + raf.readInt());
						System.out.print("\t" + raf.readInt());
						System.out.print("\t" + raf.readInt());
						System.out.print("\t" + raf.readInt());
						System.out.print("\t\t" + raf.readInt() + "\n\n");
						
						return true ;
						
					} else {
						
						nombreEncontrado = "" ;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
		System.out.println();
		return false ;
	}
	
	/*
	 * Método borrarEquipo
	 * Marcará el registro cuya columna "nombre" coincida con el nombre indicado por el usuario, para borrado (primero byte 0)
	 * Las columnas que no son el nombre se marcan con "-1"
	 */
	public static void borrarEquipo() {
		Scanner scnStr = new Scanner(System.in) ;
		int cantidadRegistros = (int)fich.length() / longitudRegistros ;
		String nombreEncontrado = "" ;
		int registroModificar = -1 ;
		
		System.out.print("Introduzca el nombre del equipo: ");
		String nombreBuscado = scnStr.nextLine().toLowerCase() ;
		
		try (RandomAccessFile raf = new RandomAccessFile(fich, "rw")) {
			
			for (int i = 0 ; i <cantidadRegistros ; i ++) {
				
				raf.seek(i * longitudRegistros);
				
				//Lectura del nombre (String)
				for (int j = 0 ; j < 30 ; j ++) {
					nombreEncontrado = nombreEncontrado + raf.readChar() ;
				}
				
				if (nombreEncontrado.toLowerCase().trim().equals(nombreBuscado)) {
					
					registroModificar = i*longitudRegistros ;
					
				} else {
					
					nombreEncontrado = "" ;
				}
			}
		
			if (registroModificar == -1) {
				System.out.println("No se ha encontrado el equipo: " + nombreBuscado + "\n");
			} else {
				raf.seek(registroModificar);
				StringBuffer sb = new StringBuffer("") ;
				sb.setLength(30);
				raf.writeChars(sb.toString());
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);
				
				System.out.println("Se ha eliminado el equipo: " + nombreBuscado + "\n");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
