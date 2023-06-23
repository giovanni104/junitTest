package org.gha.appmockito.ejemplos.services;

import org.gha.appmockito.ejemplos.models.Examen;
import org.gha.appmockito.ejemplos.repositories.ExamenRepository;



import java.util.List;
import java.util.Optional;

public class ExamenServiceImpl implements ExamenService{
    private ExamenRepository examenRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository ) {
        this.examenRepository = examenRepository;
       
    }
 

    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) {
        return examenRepository.findAll()
                .stream()
                .filter(e -> e.getNombre().contains(nombre))
                .findFirst();
    }




	@Override
	public Examen findExamenPorNombreConPreguntas(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Examen guardar(Examen examen) {
		// TODO Auto-generated method stub
		return null;
	}




}
