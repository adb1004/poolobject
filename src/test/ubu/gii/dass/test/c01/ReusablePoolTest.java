/**
 * 
 */
package test.ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import main.ubu.gii.dass.c01.DuplicatedInstanceException;
import main.ubu.gii.dass.c01.NotFreeInstanceException;
import main.ubu.gii.dass.c01.Reusable;
import main.ubu.gii.dass.c01.ReusablePool;

/**
 * @author Iván Cortés Aliende
 * @author Alberto Diez Busto
 *
 */
public class ReusablePoolTest {
	
	private ReusablePool pool;
	
	

	/**
	 * Codigo que se ejecuta antes de cada test. En este caso, se guarda la instancia del pool
	 */
	@Before
	public void setUp() throws Exception {
		pool = ReusablePool.getInstance();
	}

	/**
	 * Codigo que se ejecuta despues de cada test. En este caso, se vacian todos los elementos
	 * Reusables del pool.
	 */
	@After
	public void tearDown() throws Exception {
		while(true) {
			try {
				pool.acquireReusable();
			}catch (NotFreeInstanceException exception) {
				break;
			}
		}
	}

	/**
	 * Test que prueba que siempre se devuelva la misma instancia. Para ello, creamos otro pool,
	 * almacenamos la instancia devuelta y comprobamos que sea identico al que ya tenemos.
	 */
	@Test
	public void testGetInstance() {
		ReusablePool pool2 = ReusablePool.getInstance();
		
		assertEquals(pool,pool2);
	}

	/**
	 * Test que prueba que al adquirir un reusable este salga del pool. Basicamente, lo que
	 * hacemos es vaciarlo completamente y contar cuantos elementos ha devuelto.
	 */
	@Test
	public void testAcquireReusable() {
		
		Reusable objeto;
		int contador = 0;
				
		while(true) {
			try {
				objeto = pool.acquireReusable();
				if (objeto != null) {
					contador++;
				}
			}catch(NotFreeInstanceException exception) {
				System.out.println(contador);
				assertTrue(contador <= 2);
				break;
			}
		}
	}

	/**
	 * Test que pone a prueba el metodo encargado de introducir nuevos elementos al pool. Este 
	 * no deberia dejarnos introducir un elemento repetido. Para ello, declaramos un elemento Reusable
	 * nuevo y lo intentamos introducir dos veces. La primera deberia dejarnos, pero la segunda deberia
	 * devolvernos una excepcion de instancia duplicada.
	 */
	@Test
	public void testReleaseReusable() {
		Reusable objeto = new Reusable();
		Boolean exito = false;
		
		try{
			// Agregamos el objeto. No debería encontrarse en el pool.
			pool.releaseReusable(objeto);
			exito = true;
		}catch (DuplicatedInstanceException exception) {
			fail("Excepcion no esperada, ya existe el objeto");
		}
		
		try{
			// Agregamos el objeto otra vez. Ahora si que deberia encontrarse, por lo que deberia saltar
			// la excepcion
			pool.releaseReusable(objeto);
		}catch (DuplicatedInstanceException exception) {
			assertTrue(true);
			
		}
		
		// Retiramos el objeto introducido para no interferir en futuros test
		if (exito == true) {
			try{
				pool.acquireReusable();
			}catch (NotFreeInstanceException exception2) {}
		}
	}

}
