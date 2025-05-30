package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;



public class Matricula {
    public static final int MAXIMO_MESES_ANTERIOR_ANULACION = 6;
    public static final int MAXIMO_DIAS_ANTERIOR_MATRICULA = 15;
    public static int MAXIMO_NUMERO_HORAS_MATRICULA = 1000;
    public static final int MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA = 10;
    private static final String ER_CURSO_ACADEMICO = "[0-9]{2}-[0-9]{2}";
    public static final String FORMATO_FECHA = "dd/MM/YYYY";
    private Alumno alumno;
    private Asignatura[] coleccionAsignaturas;

    private  int idMatricula;
    private  String cursoAcademico;
    private LocalDate fechaMatriculacion;
    private LocalDate fechaAnulacion;

    public Matricula(int idMatricula, String cursoAcademico, LocalDate fechaMatriculacion, Alumno alumno, Asignatura[] coleccionAsignaturas) throws OperationNotSupportedException {

        setAlumno(alumno);
        setIdMatricula(idMatricula);
        setCursoAcademico(cursoAcademico);
        setFechaMatriculacion(fechaMatriculacion);
        setColeccionAsignaturas(coleccionAsignaturas);


    }

    public Matricula(Matricula matricula)throws OperationNotSupportedException {
        if (matricula == null) {
            throw new NullPointerException("ERROR: No es posible copiar una matrícula nula.");

        }

        this.idMatricula = matricula.idMatricula;
        this.cursoAcademico = matricula.cursoAcademico;
        if (matricula.fechaAnulacion!=null){
            this.fechaAnulacion=matricula.fechaAnulacion;
        }
        this.fechaMatriculacion = matricula.fechaMatriculacion;
        this.alumno = matricula.alumno;
        this.coleccionAsignaturas = matricula.coleccionAsignaturas;
    }


    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {


        if (idMatricula <= 0) {

            throw new IllegalArgumentException("ERROR: El identificador de una matrícula no puede ser menor o igual a 0.");
        }


        this.idMatricula = idMatricula;
    }

    public LocalDate getFechaMatriculacion() {
        return fechaMatriculacion;
    }

    public void setFechaMatriculacion(LocalDate fechaMatriculacion) {

        if (fechaMatriculacion == null) {
            throw new NullPointerException("ERROR: La fecha de matriculación de una mátricula no puede ser nula.");
        }

        DateTimeFormatter fechaMatriculacionFormateada = DateTimeFormatter.ofPattern(FORMATO_FECHA);
        fechaMatriculacion.format(fechaMatriculacionFormateada);

        LocalDate fechaAnterior = LocalDate.now().minusDays(MAXIMO_DIAS_ANTERIOR_MATRICULA);

        if (fechaMatriculacion.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("ERROR: La fecha de matriculación no puede ser posterior a hoy.");
        } else if (fechaMatriculacion.isBefore(fechaAnterior)) {
            throw new IllegalArgumentException("ERROR: La fecha de matriculación no puede ser anterior a " + MAXIMO_DIAS_ANTERIOR_MATRICULA + " días.");
        }

        this.fechaMatriculacion = fechaMatriculacion;
    }

    public String getCursoAcademico() {
        return cursoAcademico;
    }

    public void setCursoAcademico(String cursoAcademico) {

        if (cursoAcademico == null) {
            throw new NullPointerException("ERROR: El curso académico de una matrícula no puede ser nulo.");
        }
        if (cursoAcademico.isBlank()) {
            throw new IllegalArgumentException("ERROR: El curso académico de una matrícula no puede estar vacío.");
        }

        if (!cursoAcademico.matches(ER_CURSO_ACADEMICO)) {
            throw new IllegalArgumentException("ERROR: El formato del curso académico no es correcto.");
        }

        this.cursoAcademico = cursoAcademico;
    }

    public LocalDate getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(LocalDate fechaAnulacion) {

        if (fechaAnulacion.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("ERROR: La fecha de anulación de una matrícula no puede ser posterior a hoy.");
        }


        if (fechaAnulacion.isBefore(getFechaMatriculacion())){
            throw new IllegalArgumentException("ERROR: La fecha de anulación no puede ser anterior a la fecha de matriculación.");
        }




        this.fechaAnulacion = fechaAnulacion;
    }

    public void setAlumno(Alumno alumno) {
        if (alumno == null) {
            throw new NullPointerException("ERROR: El alumno de una matrícula no puede ser nulo.");
        }
        this.alumno = alumno;
    }


    public Alumno getAlumno() {
        return alumno;
    }

    public void setColeccionAsignaturas(Asignatura[] coleccionAsignaturas)throws OperationNotSupportedException{
        if (coleccionAsignaturas == null) {
            throw new NullPointerException("ERROR: La lista de asignaturas de una matrícula no puede ser nula.");
        }
        if (superaMaximoNumeroHoras(coleccionAsignaturas)){
            throw new OperationNotSupportedException("ERROR: No se puede realizar la matrícula ya que supera el máximo de horas permitidas (1000 horas).");
        }

        this.coleccionAsignaturas = coleccionAsignaturas;
    }

    public Asignatura[] getColeccionAsignaturas() {
        return coleccionAsignaturas;
    }

    private boolean superaMaximoNumeroHoras(Asignatura[] asignaturasMatricula) {
        int horasTotal = 0;
        for (Asignatura asignatura : asignaturasMatricula) {

            if (asignatura!=null){
                horasTotal = horasTotal + asignatura.getHorasAnuales();
            }
        }
        if (horasTotal > MAXIMO_NUMERO_HORAS_MATRICULA) {


            return true;

        } else{
            return false;
        }

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matricula matricula = (Matricula) o;
        return idMatricula == matricula.idMatricula;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idMatricula);
    }

    public String asignaturasMatricula() {

        return "Asignaturas de la matricula=" + coleccionAsignaturas;
    }

    public String imprimir() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "idMatricula=" + idMatricula +
                ", curso académico=" + cursoAcademico +
                ", fecha matriculación=" + fechaMatriculacion.format(formato) +
                ", alumno="+"{Nombre: " +alumno.getNombre() + "\n"+"DNI: "+ alumno.getDni()+ "\n"+"Correo: "+ alumno.getCorreo()+ "\n"+"Teléfono: "+alumno.getTelefono()+ "\n"+"Fecha de nacimiento: "+ alumno.getFechaNacimiento().format(formato)+"}";
    }



    @Override
    public String toString() {

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(FORMATO_FECHA);

        StringBuilder resultado= new StringBuilder();

        for(Asignatura asignatura : coleccionAsignaturas)
        {
            if (asignatura!=null)
                resultado.append(asignatura.imprimir());
        }

        if (fechaAnulacion == null) {
            return String.format("idMatricula=%d, curso académico=%s, fecha matriculación=%s, alumno=%s, Asignaturas={ %s}"
                    ,idMatricula,cursoAcademico,fechaMatriculacion.format(formatoFecha),alumno.imprimir(),resultado.toString());
        } else {
            return String.format(
                    "idMatricula=%d, curso académico=%s, fecha matriculación=%s, fecha anulación=%s, alumno=%s, Asignaturas={ %s}",
                    idMatricula,
                    cursoAcademico,
                    fechaMatriculacion.format(formatoFecha),
                    fechaAnulacion.format(formatoFecha),
                    alumno.imprimir(),
                    resultado.toString());
        }
    }
}
