package org.gha.junit5app.ejemplos.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CuentaTest {

	@Test
	void testNombreCuenta() {

		Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
		String esperado = "Andres";
		String real = cuenta.getPersona();
		assertEquals(esperado, real);

	}

	@Test
	@DisplayName("el saldo, que no sea null, mayor que cero, valor esperado.")
	void testSaldoCuenta() {
		Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
		assertNotNull(cuenta.getSaldo());
		assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
		assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
		assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
	}

	@Test
	@DisplayName("testeando referencias que sean iguales con el método equals.")
	void testReferenciaCuenta() {
		Cuenta cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
		Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));

//    assertNotEquals(cuenta2, cuenta);
		assertEquals(cuenta2, cuenta);

	}

	@Test
	void testDebitoCuenta() {
		Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
		cuenta.debito(new BigDecimal(100));
		assertNotNull(cuenta.getSaldo());
		assertEquals(900, cuenta.getSaldo().intValue());
		assertEquals("900.12345", cuenta.getSaldo().toPlainString());
	}

	@Test
	void testCreditoCuenta() {
		Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
		cuenta.credito(new BigDecimal(100));
		assertNotNull(cuenta.getSaldo());
		assertEquals(1100, cuenta.getSaldo().intValue());
		assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
	}

}
