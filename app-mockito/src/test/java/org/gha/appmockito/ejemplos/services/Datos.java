package org.gha.appmockito.ejemplos.services;


import java.util.Arrays;
import java.util.List;

import org.gha.appmockito.ejemplos.models.Examen;

public class Datos {
    public final static List<Examen> EXAMENES = Arrays.asList(new Examen(5L, "Matem�ticas"),
            new Examen(6L, "Lenguaje"),
            new Examen(7L, "Historia"));

    public final static List<Examen> EXAMENES_ID_NULL = Arrays.asList(new Examen(null, "Matem�ticas"),
            new Examen(null, "Lenguaje"),
            new Examen(null, "Historia"));

    public final static List<Examen> EXAMENES_ID_NEGATIVOS = Arrays.asList(new Examen(-5L, "Matem�ticas"),
            new Examen(-6L, "Lenguaje"),
            new Examen(null, "Historia"));

    public final static List<String> PREGUNTAS = Arrays.asList("aritm�tica","integrales",
            "derivadas", "trigonometr�a", "geometr�a");
    public final static Examen EXAMEN = new Examen(null, "F�sica");
}