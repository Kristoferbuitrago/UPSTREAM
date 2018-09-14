package co.udea.edu.edatos.dao.impl;

import co.udea.edu.edatos.dao.EstudianteDAO;
import co.udea.edu.edatos.dao.exception.LlaveDuplicadaException;
import co.udea.edu.edatos.modelo.Estudiante;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAOFile implements EstudianteDAO {

    private static final String nombreArchivo = "estudiantes";

    @Override
    public void crearEstudiante(Estudiante estudiante) throws LlaveDuplicadaException {
        String s = convertirEstudianteACaracteres(estudiante);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true));
            writer.write(s);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Estudiante consultarEstudiante(String id) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            String st;
            while ((st = br.readLine()) != null) {
                System.out.println(st);
            }
            String[] estudiantesCaracteres = st.split(";");
            for (int i = 0; i < estudiantesCaracteres.length; i++) {
                Estudiante estudiante = convertirCaracteresAEstudiante(estudiantesCaracteres[i]);
                if (estudiante.getId().equals(id)) {
                    return estudiante;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Estudiante> consultarEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            String st;
            String[] estudiantesCaracteres = null;
            while ((st = br.readLine()) != null) {
                System.out.println(st);
                estudiantesCaracteres = st.split(";");
            }

            for (int i = 0; i < estudiantesCaracteres.length; i++) {
                Estudiante estudiante = convertirCaracteresAEstudiante(estudiantesCaracteres[i]);
                estudiantes.add(estudiante);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return estudiantes;
    }

    @Override
    public void eliminarEstudiante(String id) {

    }

    @Override
    public void actualizarEstudiante(Estudiante estudiante) {

    }

    private String convertirEstudianteACaracteres(Estudiante estudiante) {
        StringBuilder estudianteString = new StringBuilder();
        estudianteString.append(estudiante.getId()).append(",");
        estudianteString.append(estudiante.getNombres()).append(",");
        estudianteString.append(estudiante.getApellidos()).append(",");
        estudianteString.append(estudiante.getEdad()).append(",");
        estudianteString.append(estudiante.getPrograma()).append(";");
        return estudianteString.toString();
    }

    private Estudiante convertirCaracteresAEstudiante(String estudiante) {
        String[] estudianteCaracteres = estudiante.split(",");
        Estudiante estudianteObjeto = new Estudiante(estudianteCaracteres[0],
                estudianteCaracteres[1],
                estudianteCaracteres[2],
                Integer.parseInt(estudianteCaracteres[3]),
                Integer.parseInt(estudianteCaracteres[4]));
        return estudianteObjeto;
    }
}
