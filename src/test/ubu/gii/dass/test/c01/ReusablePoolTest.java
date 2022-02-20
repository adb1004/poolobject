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
	private int testPasados = 0;
	
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pool = ReusablePool.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
		if (testPasados == 3) {
			while(true) {
				try {
					pool.acquireReusable();
				}catch (NotFreeInstanceException exception) {
					break;
				}
			}
		}
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		ReusablePool pool2 = ReusablePool.getInstance();
		
		assertEquals(pool,pool2);
		testPasados++;
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
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
				assertTrue(contador == 2);
				break;
			}
		}
		testPasados++;
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
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
		
		// Retiramos el objeto introducido
		if (exito == true) {
			try{
				pool.acquireReusable();
			}catch (NotFreeInstanceException exception2) {}
		}
		testPasados++;	
	}

}
