package co.udea.edu.edatos.dao.impl;

import co.udea.edu.edatos.dao.EstudianteDAO;
import co.udea.edu.edatos.dao.exception.LlaveDuplicadaException;
import co.udea.edu.edatos.modelo.Estudiante;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.WRITE;

public class EstudianteDAONIOFile implements EstudianteDAO {

    private static final String NOMBRE_ARCHIVO = "estudiantes-nio";
    private static final int LONGITUD_REGISTRO = 80;
    private static final int LONGITUD_ID = 10;
    private static final int LONGITUD_NOMBRES = 32;
    private static final int LONGITUD_APELLIDOS = 32;
    private static final int LONGITUD_EDAD = 3;
    private static final int LONGITUD_PROGRAMA = 3;
    private static final String ENCODING_WINDOWS = "ASCII";

    private static final Path archivo = Paths.get(NOMBRE_ARCHIVO);
    private static final Map<String, Integer> indice = new HashMap<>();
    private static int direccion=0;

    public EstudianteDAONIOFile(){
        if(!Files.exists(archivo)){
            try {
                Files.createFile(archivo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        crearIndice();
    }

    private void crearIndice() {
        System.out.println("Creando índice");
        try(SeekableByteChannel sbc = Files.newByteChannel(archivo)){
            ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
            while (sbc.read(buffer)>0){
                //devuelve el apuntador del buffer al principio
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_WINDOWS).decode(buffer);
                Estudiante estudianteConvertido = parseEstudiante2Objeto(registro);
                indice.put(estudianteConvertido.getId(), direccion++);
                buffer.flip();
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }


    @Override
    public void crearEstudiante(Estudiante estudiante) throws LlaveDuplicadaException {
        Estudiante estudianteConsultado = consultarEstudiante(estudiante.getId());
        if(estudianteConsultado!=null){
            throw new LlaveDuplicadaException();
        }
        String registro = parseEstudiante2String(estudiante);
        byte[] datos = registro.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(datos);
        try(FileChannel fc = (FileChannel.open(archivo, APPEND))){
            fc.write(buffer);
            indice.put(estudiante.getId(),direccion++);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override
    public void actualizarEstudiante(Estudiante estudiante) {
        Integer direccionEstudianteActual = indice.get(estudiante.getId());
        if(direccionEstudianteActual!=null) {
            String registro = parseEstudiante2String(estudiante);
            byte[] datos = registro.getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(datos);
            try (FileChannel fc = (FileChannel.open(archivo, WRITE))) {
                fc.position(direccionEstudianteActual*LONGITUD_REGISTRO);
                fc.write(buffer);
                indice.put(estudiante.getId(), direccionEstudianteActual);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }



    @Override
    public Estudiante consultarEstudiante(String id) {
        Integer direccion = indice.get(id);
        if(direccion !=null){
            System.out.println("Encontró el id en el índice, usa la dirección para ir a memoria secundaria");
            try(SeekableByteChannel sbc = Files.newByteChannel(archivo)){
                ByteBuffer buffer = ByteBuffer.allocate(LONGITUD_REGISTRO);
                sbc.position(direccion*LONGITUD_REGISTRO);
                sbc.read(buffer);
                //devuelve el apuntador del buffer al principio
                buffer.rewind();
                CharBuffer registro = Charset.forName(ENCODING_WINDOWS).decode(buffer);
                Estudiante estudianteConvertido = parseEstudiante2Objeto(registro);
                return estudianteConvertido;
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
        System.out.println("No se encontró el registró en el índice");
        return null;

    }


    @Override
    public List<Estudiante> consultarEstudiantes() {
        return null;
    }

    @Override
    public void eliminarEstudiante(String id) {

    }

    private Estudiante parseEstudiante2Objeto(CharBuffer registro) {
        Estudiante estudiante = new Estudiante();
        String identificacion = registro.subSequence(0, LONGITUD_ID).toString().trim();
        registro.position(LONGITUD_ID);
        registro = registro.slice();
        estudiante.setId(identificacion);

        String nombres = registro.subSequence(0, LONGITUD_NOMBRES).toString().trim();
        registro.position(LONGITUD_NOMBRES);
        registro = registro.slice();
        estudiante.setNombres(nombres);

        String apellidos = registro.subSequence(0, LONGITUD_APELLIDOS).toString().trim();
        registro.position(LONGITUD_APELLIDOS);
        registro = registro.slice();
        estudiante.setApellidos(apellidos);

        int edad = Integer.parseInt(registro.subSequence(0, LONGITUD_EDAD).toString().trim());
        registro.position(LONGITUD_EDAD);
        registro = registro.slice();
        estudiante.setEdad(edad);

        int programaAcademico = Integer.parseInt(registro.toString().trim());
        estudiante.setPrograma(programaAcademico);

        return estudiante;

    }

    private String parseEstudiante2String(Estudiante estudiante) {
        StringBuilder registro = new StringBuilder();
        registro.append(completarCampo(estudiante.getId(), LONGITUD_ID));
        registro.append(completarCampo(estudiante.getNombres(), LONGITUD_NOMBRES));
        registro.append(completarCampo(estudiante.getApellidos(), LONGITUD_APELLIDOS));
        registro.append(completarCampo(String.valueOf(estudiante.getEdad()), LONGITUD_EDAD));
        registro.append(completarCampo(String.valueOf(estudiante.getPrograma()), LONGITUD_PROGRAMA));
        return registro.toString();
    }

    private String completarCampo(String campo, int tamanio){
        if(campo.length()>tamanio){
            return campo.substring(0, tamanio);
        }
        return String.format("%1$-"+tamanio+"s", campo);
    }
}
