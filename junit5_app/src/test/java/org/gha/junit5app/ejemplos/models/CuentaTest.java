package org.gha.junit5app.ejemplos.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import org.gha.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CuentaTest {

	Cuenta cuenta;

	@BeforeEach
	void initMetodoTest() {
		this.cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
		System.out.println("Inicializando el metodo de prueba.");
	}

	@AfterEach
	void tearDown() {
		System.out.println("finalizando el metodo de prueba.");
	}

	@BeforeAll
	static void beforeAll() {
		System.out.println("inicializando el test");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("finalizando el test");
	}

	@Test
	@DisplayName("el nombre!")
	void testNombreCuenta() {

		String esperado = "Andres";
		String real = cuenta.getPersona();

		assertNotNull(real, () -> "la cuenta no puede ser nula");
		assertEquals(esperado, real, () -> "el nombre de la cuenta no es el que se esperaba: se esperaba " + esperado
				+ " sin embargo fue " + real);
		assertTrue(real.equals("Andres"), () -> "nombre cuenta esperada debe ser igual a la real");

	}

	@Test
	@DisplayName("el saldo, que no sea null, mayor que cero, valor esperado.")
	void testSaldoCuenta() {

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

		cuenta.debito(new BigDecimal(100));
		assertNotNull(cuenta.getSaldo());
		assertEquals(900, cuenta.getSaldo().intValue());
		assertEquals("900.12345", cuenta.getSaldo().toPlainString());
	}

	@Test
	void testCreditoCuenta() {

		cuenta.credito(new BigDecimal(100));
		assertNotNull(cuenta.getSaldo());
		assertEquals(1100, cuenta.getSaldo().intValue());
		assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
	}

	@Test
	void testDineroInsuficienteExceptionCuenta() {

		Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
			cuenta.debito(new BigDecimal(1500));
		});
		String actual = exception.getMessage();
		String esperado = "Dinero Insuficiente";
		assertEquals(esperado, actual);
	}

	@Test
	void testTransferirDineroCuentas() {
		Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
		Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

		Banco banco = new Banco();
		banco.setNombre("Banco del Estado");
		banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
		assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
		assertEquals("3000", cuenta1.getSaldo().toPlainString());
	}

	@Test
	@Tag("cuenta")
	@Tag("banco")
	// @Disabled
	@DisplayName("probando relaciones entre las cuentas y el banco con assertAll.")
	void testRelacionBancoCuentas() {
		// fail();
		Cuenta cuenta1 = new Cuenta("Jhon Doe", new BigDecimal("2500"));
		Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

		Banco banco = new Banco();
		banco.addCuenta(cuenta1);
		banco.addCuenta(cuenta2);

		banco.setNombre("Banco del Estado");
		banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
		assertAll(
				() -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(),
						() -> "el valor del saldo de la cuenta2 no es el esperado."),

				() -> assertEquals("3000", cuenta1.getSaldo().toPlainString(),
						() -> "el valor del saldo de la cuenta1 no es el esperado."),

				() -> assertEquals(2, banco.getCuentas().size(), () -> "el banco no tienes las cuentas esperadas"),

				() -> assertEquals("Banco del Estado", cuenta1.getBanco().getNombre()),

				() -> assertEquals("Andres",
						banco.getCuentas().stream().filter(c -> c.getPersona().equals("Andres")).findFirst().get()
								.getPersona()),

				() -> assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals("Jhon Doe"))));
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	void testSoloWindows() {
	}

	@Test
	@EnabledOnOs({ OS.LINUX, OS.MAC })
	void testSoloLinuxMac() {
	}

	@Test
	@DisabledOnOs(OS.WINDOWS)
	void testNoWindows() {
	}
	
	@Test
    @EnabledOnJre(JRE.JAVA_8)
    void soloJdk8() {
    }

	@Test
    @EnabledOnJre(JRE.JAVA_9)
    void soloJdk9() {
    }
	
	
    @Test
    @EnabledOnJre(JRE.JAVA_15)
    void soloJDK15() {
    }

    @Test
    @EnabledOnJre(JRE.OTHER)
    void soloJDKOTHER() {
    }
    
    
    @Test
    @DisabledOnJre(JRE.JAVA_15)
    void testNoJDK15() {
    }

    @Test
    void imprimirSystemProperties() {
        Properties properties = System.getProperties();
        properties.forEach((k, v)-> System.out.println(k + ":" + v));
    }
    
    
    @Test
    void imprimirVariablesAmbiente() {
    	System.out.println( "----------------------------------------------");
        Map<String, String> getenv = System.getenv();
        getenv.forEach((k, v)-> System.out.println(k + " = " + v));
    }
    
    
    
    
    
    
}
