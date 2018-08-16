package co.udea.edu.edatos.negocio;

import co.udea.edu.edatos.modelo.Estudiante;
import co.udea.edu.edatos.negocio.impl.EstudianteYaExisteException;

import java.util.List;

public interface EstudianteBsn {

    void crearEstudiante(Estudiante estudiante) throws EstudianteYaExisteException;

    List<Estudiante> listarEstudiantes();
}
