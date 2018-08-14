package co.udea.edatos.controlador;

import co.udea.edatos.modelo.Estudiante;
import co.udea.edu.edatos.negocio.EstudianteBsn;
import co.udea.edu.edatos.negocio.impl.EstudianteBsnImpl;
import co.udea.edu.edatos.negocio.impl.EstudianteYaExisteException;

import java.util.List;
import java.util.Scanner;

public class EstudianteControlador {

    private static EstudianteBsn estudianteBsn = new EstudianteBsnImpl();

    public static void main(String args[]){
        StringBuilder menu = new StringBuilder();
        menu.append("Bienvenido a la aplicación:").append(System.getProperty("line.separator"));
        menu.append("1. Registrar estudiante").append(System.getProperty("line.separator"));
        menu.append("2. Consultar estudiante").append(System.getProperty("line.separator"));
        menu.append("3. Ver todos los estudiantes").append(System.getProperty("line.separator"));
        menu.append("4. Salir").append(System.getProperty("line.separator"));

        Scanner lector = new Scanner(System.in);
        String opcion="";

        while(!"4".equals(opcion)){
            System.out.println(menu);
            opcion = lector.next();

            switch (opcion){
                case "1":
                    System.out.println("Ingrese el id: ");
                    String id = lector.next();
                    System.out.println("Ingrese los nombres: ");
                    String nombres = lector.next();
                    System.out.println("Ingrese los apellidos: ");
                    String apellidos = lector.next();
                    System.out.println("Ingrese la edad del estudiante: ");
                    int edad = lector.nextInt();
                    System.out.println("Ingrese el programa académico del estudiante: ");
                    int programa = lector.nextInt();

                    Estudiante estudianteNuevo = new Estudiante(id, nombres, apellidos, edad, programa);
                    try{
                        estudianteBsn.crearEstudiante(estudianteNuevo);
                        System.out.println("Estudiante almacenado exitosamente");
                    }catch (EstudianteYaExisteException eyee){
                        System.err.println(eyee.toString());
                    }
                    break;

                case "2":
                    System.out.println("función no implementada aún");
                    break;
                case "3":
                    List<Estudiante> estudiantes = estudianteBsn.listarEstudiantes();
                    estudiantes.forEach(System.out::println);
                    break;

            }
        }


    }

}
