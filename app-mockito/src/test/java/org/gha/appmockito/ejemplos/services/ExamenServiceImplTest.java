package org.gha.appmockito.ejemplos.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.gha.appmockito.ejemplos.models.Examen;
import org.gha.appmockito.ejemplos.repositories.ExamenRepository;
import org.gha.appmockito.ejemplos.repositories.ExamenRepositoryImpl;
import org.gha.appmockito.ejemplos.repositories.PreguntaRepository;
import org.gha.appmockito.ejemplos.repositories.PreguntaRepositoryImpl;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {

	@Mock
	ExamenRepositoryImpl repository;

	@Mock
	PreguntaRepositoryImpl preguntaRepository;

	@InjectMocks
	ExamenServiceImpl service;

	@Captor
	ArgumentCaptor<Long> captor;

	@BeforeEach
	void setUp() {
//        MockitoAnnotations.openMocks(this);
//        repository = mock(ExamenRepository.class);
//        preguntaRepository = mock(PreguntaRepository.class);
//        service = new ExamenServiceImpl(repository, preguntaRepository);
	}

	@Test
	void findExamenPorNombre() {

		when(repository.findAll()).thenReturn(Datos.EXAMENES);

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

	@Test
	void testPreguntasExamen() {
		when(repository.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
		Examen examen = service.findExamenPorNombreConPreguntas("Matemáticas");
		assertEquals(5, examen.getPreguntas().size());
		assertTrue(examen.getPreguntas().contains("integrales"));

	}

	@Test
	void testPreguntasExamenVerify() {
		when(repository.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
		Examen examen = service.findExamenPorNombreConPreguntas("Matemáticas");
		assertEquals(5, examen.getPreguntas().size());
		assertTrue(examen.getPreguntas().contains("integrales"));
		verify(repository).findAll();
		verify(preguntaRepository).findPreguntasPorExamenId(anyLong());

	}

	@Test
	void testNoExisteExamenVerify() {
		// given
		when(repository.findAll()).thenReturn(Collections.emptyList());
		when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

		// when
		Examen examen = service.findExamenPorNombreConPreguntas("Matemáticas");

		// then
		assertNull(examen);
		verify(repository).findAll();
		verify(preguntaRepository).findPreguntasPorExamenId(5L);
	}

	@Test
	void testGuardarExamen() {
		// Given
		Examen newExamen = Datos.EXAMEN;
		newExamen.setPreguntas(Datos.PREGUNTAS);

		when(repository.guardar(any(Examen.class))).then(new Answer<Examen>() {

			Long secuencia = 8L;

			@Override
			public Examen answer(InvocationOnMock invocation) throws Throwable {
				Examen examen = invocation.getArgument(0);
				examen.setId(secuencia++);
				return examen;
			}
		});

		// When
		Examen examen = service.guardar(newExamen);

		// Then
		assertNotNull(examen.getId());
		assertEquals(8L, examen.getId());
		assertEquals("Física", examen.getNombre());

		verify(repository).guardar(any(Examen.class));
		verify(preguntaRepository).guardarVarias(anyList());
	}

	@Test
	void testManejoException() {
		when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NULL);
		when(preguntaRepository.findPreguntasPorExamenId(isNull())).thenThrow(new IllegalArgumentException());
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			service.findExamenPorNombreConPreguntas("Matemáticas");
		});
		assertEquals(IllegalArgumentException.class, exception.getClass());

		verify(repository).findAll();
		verify(preguntaRepository).findPreguntasPorExamenId(isNull());

	}

	@Test
	void testArgumentMatchers() {
		when(repository.findAll()).thenReturn(Datos.EXAMENES);
		when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
		service.findExamenPorNombreConPreguntas("Matemáticas");

		verify(repository).findAll();
//        verify(preguntaRepository).findPreguntasPorExamenId(argThat(arg -> arg != null && arg.equals(5L)));
		verify(preguntaRepository).findPreguntasPorExamenId(argThat(arg -> arg != null && arg >= 5L));
//        verify(preguntaRepository).findPreguntasPorExamenId(eq(5L));

	}

	@Test
	void testArgumentMatchers2() {
		when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
		when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
		service.findExamenPorNombreConPreguntas("Matemáticas");

		verify(repository).findAll();
		verify(preguntaRepository).findPreguntasPorExamenId(argThat(new MiArgsMatchers()));

	}

	@Test
	void testArgumentMatchers3() {
		when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
		when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
		service.findExamenPorNombreConPreguntas("Matemáticas");

		verify(repository).findAll();
		verify(preguntaRepository).findPreguntasPorExamenId(argThat((argument) -> argument != null && argument > 0));

	}

	public static class MiArgsMatchers implements ArgumentMatcher<Long> {

		private Long argument;

		@Override
		public boolean matches(Long argument) {
			this.argument = argument;
			return argument != null && argument > 0;
		}

		@Override
		public String toString() {
			return "es para un mensaje personalizado de error " + "que imprime mockito en caso de que falle el test "
					+ argument + " debe ser un entero positivo";
		}
	}

	@Test
	void testArgumentCaptor() {
		when(repository.findAll()).thenReturn(Datos.EXAMENES);
//        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
		service.findExamenPorNombreConPreguntas("Matemáticas");

		// ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
		verify(preguntaRepository).findPreguntasPorExamenId(captor.capture());

		assertEquals(5L, captor.getValue());
	}

	@Test
	void testDoThrow() {
		Examen examen = Datos.EXAMEN;
		examen.setPreguntas(Datos.PREGUNTAS);
		doThrow(IllegalArgumentException.class).when(preguntaRepository).guardarVarias(anyList());

		assertThrows(IllegalArgumentException.class, () -> {
			service.guardar(examen);
		});
	}
	
	 @Test
	    void testDoAnswer() {
	        when(repository.findAll()).thenReturn(Datos.EXAMENES);
//	        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
	        doAnswer(invocation -> {
	            Long id = invocation.getArgument(0);
	            return id == 5L? Datos.PREGUNTAS: Collections.emptyList();
	        }).when(preguntaRepository).findPreguntasPorExamenId(anyLong());

	        Examen examen = service.findExamenPorNombreConPreguntas("Matemáticas");
	        assertEquals(5, examen.getPreguntas().size());
	        assertTrue(examen.getPreguntas().contains("geometría"));
	        assertEquals(5L, examen.getId());
	        assertEquals("Matemáticas", examen.getNombre());

	        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
	    }
	 
	 
	    @Test
	    void testDoAnswerGuardarExamen() {
	        // Given
	        Examen newExamen = Datos.EXAMEN;
	        newExamen.setPreguntas(Datos.PREGUNTAS);

	        doAnswer(new Answer<Examen>(){

	            Long secuencia = 8L;

	            @Override
	            public Examen answer(InvocationOnMock invocation) throws Throwable {
	                Examen examen = invocation.getArgument(0);
	                examen.setId(secuencia++);
	                return examen;
	            }
	        }).when(repository).guardar(any(Examen.class));

	        // When
	        Examen examen = service.guardar(newExamen);

	        // Then
	        assertNotNull(examen.getId());
	        assertEquals(8L, examen.getId());
	        assertEquals("Física", examen.getNombre());

	        verify(repository).guardar(any(Examen.class));
	        verify(preguntaRepository).guardarVarias(anyList());
	    } 
	 
	 
	 
	    @Test
	    void testDoCallRealMethod() {
	        when(repository.findAll()).thenReturn(Datos.EXAMENES);
//	        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
	        doCallRealMethod().when(preguntaRepository).findPreguntasPorExamenId(anyLong());
	        Examen examen = service.findExamenPorNombreConPreguntas("Matemáticas");
	        assertEquals(5L, examen.getId());
	        assertEquals("Matemáticas", examen.getNombre());

	    }
	 

	    @Test
	    void testSpy() {
	        ExamenRepository examenRepository = spy(ExamenRepositoryImpl.class);
	        PreguntaRepository preguntaRepository = spy(PreguntaRepositoryImpl.class);
	        ExamenService examenService = new ExamenServiceImpl(examenRepository, preguntaRepository);

	        List<String> preguntas = Arrays.asList("aritmética");
//	        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(preguntas);
	        doReturn(preguntas).when(preguntaRepository).findPreguntasPorExamenId(anyLong());

	        Examen examen = examenService.findExamenPorNombreConPreguntas("Matemáticas");
	        assertEquals(5, examen.getId());
	        assertEquals("Matemáticas", examen.getNombre());
	        assertEquals(1, examen.getPreguntas().size());
	        assertTrue(examen.getPreguntas().contains("aritmética"));

	        verify(examenRepository).findAll();
	        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
	    }
	 
	 

}
