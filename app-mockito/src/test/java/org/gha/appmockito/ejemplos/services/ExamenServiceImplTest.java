package org.gha.appmockito.ejemplos.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.gha.appmockito.ejemplos.models.Examen;
import org.gha.appmockito.ejemplos.repositories.ExamenRepository;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

class ExamenServiceImplTest {

	@Test
	void findExamenPorNombre() {
		ExamenRepository repository = mock(ExamenRepository.class);
		ExamenService service = new ExamenServiceImpl(repository);

		List<Examen> datos = Arrays.asList(new Examen(5L, "Matemáticas"), new Examen(6L, "Lenguaje"),
				new Examen(7L, "Historia"));

		when(repository.findAll()).thenReturn(datos);
		
		Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");

		assertTrue(examen.isPresent());
		assertEquals(5L, examen.orElseThrow().getId());
		assertEquals("Matemáticas", examen.get().getNombre());

	}
	
	
	
	
	
	
	
	

}
