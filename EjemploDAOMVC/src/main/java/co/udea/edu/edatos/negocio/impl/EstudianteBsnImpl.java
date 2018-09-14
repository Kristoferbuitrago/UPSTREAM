package co.udea.edu.edatos.negocio.impl;

import co.udea.edu.edatos.dao.impl.EstudianteDAOFile;
import co.udea.edu.edatos.dao.impl.EstudianteDAONIOFile;
import co.udea.edu.edatos.modelo.Estudiante;
import co.udea.edu.edatos.dao.EstudianteDAO;
import co.udea.edu.edatos.dao.exception.LlaveDuplicadaException;
import co.udea.edu.edatos.negocio.EstudianteBsn;

import java.util.List;

public class EstudianteBsnImpl implements EstudianteBsn {

    private EstudianteDAO estudianteDAO;

    public EstudianteBsnImpl(){
        estudianteDAO = new EstudianteDAONIOFile();
    }

    public void crearEstudiante(Estudiante estudiante) throws EstudianteYaExisteException {
        if(estudiante!= null && "cualquiera".equals(estudiante.getNombres())){
            System.out.println("no se puede guardar por regla de negocio");
            return;
        }
        try{
            estudianteDAO.crearEstudiante(estudiante);
        }catch(LlaveDuplicadaException llaveDuplicadaException){
            System.out.println("El estudiante ya existe, lo actualizaré");
            estudianteDAO.actualizarEstudiante(estudiante);
            throw new EstudianteYaExisteException();
        }

    }

    @Override
    public Estudiante listarEstudiante(String id) {
        return estudianteDAO.consultarEstudiante(id);
    }

    @Override
    public List<Estudiante> listarEstudiantes() {
        return estudianteDAO.consultarEstudiantes();
    }
}
