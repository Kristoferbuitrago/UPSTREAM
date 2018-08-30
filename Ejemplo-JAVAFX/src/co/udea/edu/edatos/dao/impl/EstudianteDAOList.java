package co.udea.edu.edatos.dao.impl;

import co.udea.edu.edatos.dao.EstudianteDAO;
import co.udea.edu.edatos.dao.exception.LlaveDuplicadaException;
import co.udea.edu.edatos.modelo.Estudiante;

import java.util.ArrayList;
import java.util.List;

public class EstudianteDAOList implements EstudianteDAO {

    private final static List<Estudiante> estudiantesBD = new ArrayList<Estudiante>();

    public void crearEstudiante(Estudiante estudiante) throws LlaveDuplicadaException {
        if(consultarEstudiante(estudiante.getId())!=null){
            throw new LlaveDuplicadaException();
        }
        estudiantesBD.add(estudiante);
    }

    public Estudiante consultarEstudiante(String id) {
        for(Estudiante est:estudiantesBD){
            if(est.getId().equals(id)){
                return est;
            }
        }
        return null;
    }

    public List<Estudiante> consultarEstudiantes() {
        return estudiantesBD;
    }

    public void eliminarEstudiante(String id) {
        Estudiante est = consultarEstudiante(id);
        if(est!=null){
            estudiantesBD.remove(est);
        }
    }
}
