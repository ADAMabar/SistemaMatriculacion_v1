package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum Grado {
    GDCFGB("Grado D Ciclo Formativo de Grado Básico"), GDCFGM("Grado D Ciclo formativo de Grado Medio"), GDCFGS("Grado D Ciclo Formativo de Grado Superior");

private String cadenaAMostrar;

private Grado(String cadenaAMostrar){
    this.cadenaAMostrar=cadenaAMostrar;
}
    public String imprimir() {
        return this.ordinal() + ".-" + this.cadenaAMostrar;
    }

    @Override
    public String toString() {
        return this.cadenaAMostrar;
    }
}

