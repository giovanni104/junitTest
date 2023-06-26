package org.gha.appmockito.ejemplos.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.gha.appmockito.ejemplos.models.Examen;
import org.gha.appmockito.ejemplos.repositories.ExamenRepository;
import org.gha.appmockito.ejemplos.repositories.PreguntaRepository;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ExamenServiceImplTest {

	@Mock
	ExamenRepository repository;
	
    @Mock
    PreguntaRepository preguntaRepository;
	

	@InjectMocks
	ExamenServiceImpl service;

	@BeforeEach
	void setUp() {

		repository = mock(ExamenRepository.class);
		preguntaRepository = mock(PreguntaRepository.class);
		service = new ExamenServiceImpl(repository, preguntaRepository);
	}

	@Test
	void findExamenPorNombre() {
	 

		List<Examen> datos = Arrays.asList(new Examen(5L, "Matemáticas"), new Examen(6L, "Lenguaje"),
				new Examen(7L, "Historia"));

		when(repository.findAll()).thenReturn(datos);

		Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");

		assertTrue(examen.isPresent());
		assertEquals(5L, examen.orElseThrow().getId());
		assertEquals("Matemáticas", examen.get().getNombre());

	}

	   @Test
	    void findExamenPorNombreListaVacia() {
	        List<Examen> datos = Collections.emptyList();

	        when(repository.findAll()).thenReturn(datos);
	        Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");

	        assertFalse(examen.isPresent());
	    }
	
	
	
	
	
	
}
