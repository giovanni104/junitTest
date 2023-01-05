package org.gha.junit5app.ejemplos.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CuentaTest {

	@Test
	void testNombreCuenta() {

		Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
		String esperado = "Andres";
		String real = cuenta.getPersona();
		Assertions.assertEquals(esperado, real);

	}

}
