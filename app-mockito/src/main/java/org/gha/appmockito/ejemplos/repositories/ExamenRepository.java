package org.gha.appmockito.ejemplos.repositories;

import java.util.List;

import org.gha.appmockito.ejemplos.models.Examen;

public interface ExamenRepository {
	Examen guardar(Examen examen);

	List<Examen> findAll();
}
