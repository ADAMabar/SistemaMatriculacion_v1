package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;

import javax.naming.OperationNotSupportedException;

public class Alumnos {
    private Alumno[] coleccionAlumnos;
    private int capacidad;
    private int tamano;


    public Alumnos(int capacidad){
        if (capacidad <= 0 ) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        this.tamano = 0;
        this.coleccionAlumnos = new Alumno[capacidad];
    }


    public Alumno[] get() {
        return copiaProfundaAlumnos();
    }


    private Alumno[] copiaProfundaAlumnos() {
        Alumno[] copia = new Alumno[tamano]; // Crear un nuevo arreglo con la misma capacidad

        // Iterar sobre el arreglo original y crear una nueva instancia de Alumno para cada uno
        for (int i = 0; i < tamano; i++) {
            copia[i] = new Alumno(coleccionAlumnos[i]); // Suponiendo que Alumno tiene un constructor de copia
        }

        return copia;
    }

    public int getTamano() {
        return tamano;
    }


    public int getCapacidad() {
        return capacidad;
    }

    public void insertar(Alumno alumno) throws OperationNotSupportedException{
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }
        if (buscar(alumno) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }
        if (tamano >= capacidad) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más alumnos.");
        }

        coleccionAlumnos[tamano++] =new Alumno(alumno);
    }


    public Alumno buscar(Alumno alumno) {
        if (alumno == null) {
            throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
        }

        int indice = buscarIndice(alumno);
        if (indice != -1) {
            return coleccionAlumnos[indice];
        }
        return null;
    }


    public Alumno borrar(Alumno alumno)throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }


        boolean encontrado = false;

        for (int i = 0; i < tamano; i++) {
            if (coleccionAlumnos[i].equals(alumno)) {
                desplazarUnaPosicionHaciaIzquierda(i);
                tamano--;
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new OperationNotSupportedException("ERROR: No existe ningún alumno como el indicado.");
        }

        return alumno;
    }


    private int buscarIndice(Alumno alumno) {
        for (int i = 0; i < tamano; i++) {
            if (coleccionAlumnos[i].equals(alumno)) {
                return i;
            }
        }
        return -1;
    }


    private boolean tamanoSuperado(int indice) {
        return indice >= tamano;
    }


    private boolean capacidadSuperada(int indice) {
        return indice >= capacidad;
    }


    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        for (int i = indice; i < tamano - 1; i++) {
            coleccionAlumnos[i] = coleccionAlumnos[i + 1];
        }
        coleccionAlumnos[tamano - 1] = null;
    }

}