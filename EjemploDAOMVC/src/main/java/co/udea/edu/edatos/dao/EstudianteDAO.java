package co.udea.edu.edatos.dao;
import co.udea.edu.edatos.modelo.Estudiante;
import co.udea.edu.edatos.dao.exception.LlaveDuplicadaException;

import java.util.List;

public interface EstudianteDAO {
    void crearEstudiante(Estudiante estudiante) throws LlaveDuplicadaException;
    Estudiante consultarEstudiante(String id);
    List<Estudiante> consultarEstudiantes();
    void eliminarEstudiante(String id);
}
