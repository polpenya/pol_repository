package ejercicio7JUnit;

import java.util.List;



public class CuentaBancariaHistorialTest {

    CuentaBancaria cuenta;

    @BeforeEach
    void setUp() {
        cuenta = new CuentaBancaria(100);
    }

    /**
     * Comprobar que el movimiento del ingreso inicial se guarda bien
     */
    @Test
    void historial_IngresoInicial() {
        List<Movimiento> historial = cuenta.getHistorial();
        assertEquals(1, historial.size());
        assertEquals(Tipo.DEPOSITO, historial.get(0).getTipo());
        assertEquals(100, historial.get(0).getCantidad());
    }

    /**
     * Comprobar que se añade correctamente un deposito a una cuenta que
     * ya existe con un depósito inicial
     */
    @Test
    void historial_AddDepositoCorrecto() {
        cuenta.depositar(50);
        List<Movimiento> historial = cuenta.getHistorial();
        assertEquals(2, historial.size());
        assertEquals(Tipo.DEPOSITO, historial.get(1).getTipo());
        assertEquals(150, historial.get(1).getCantidad()); // Saldo actual después del depósito
    }

    /**
     * Comprobar que no se añade un movimiento si el deposito es negativo
     */
    @Test
    void historial_AddDepositoIncorrecto() {
        cuenta.depositar(-50);
        List<Movimiento> historial = cuenta.getHistorial();
        assertEquals(1, historial.size()); // Solo el ingreso inicial debe estar
    }

    /**
     * Comprobar que se puede hacer un retiro de una cantidad correcta y 
     * se añade a los movimientos
     */
    @Test
    void historial_AddRetiroCorrecto() {
        cuenta.retirar(30);
        List<Movimiento> historial = cuenta.getHistorial();
        assertEquals(2, historial.size());
        assertEquals(Tipo.RETIRO, historial.get(1).getTipo());
        assertEquals(70, historial.get(1).getCantidad()); // Saldo restante después del retiro
    }

    /**
     * Comprobar que no se puede hacer un retiro de una cantidad negativa/incorrecta y 
     * no se añade a los movimientos
     */
    @Test
    void historial_AddRetiroIncorrecto() {
        cuenta.retirar(-30);
        cuenta.retirar(200); // mayor que el saldo
        List<Movimiento> historial = cuenta.getHistorial();
        assertEquals(1, historial.size()); // Solo el ingreso inicial
    }
}
