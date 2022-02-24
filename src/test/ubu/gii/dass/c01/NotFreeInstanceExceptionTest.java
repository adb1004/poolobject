package test.ubu.gii.dass.test.c01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.*;
import main.ubu.gii.dass.c01.*;

/**
 * @author Ivan Cortes Aliende
 * @author Alberto Diez Busto
 *
 */
public class NotFreeInstanceExceptionTest {

	private ReusablePool pool;

	@Before
	public void before(){
		pool = ReusablePool.getInstance();
		
		while(true) {
			try {
				pool.acquireReusable();
			}catch (NotFreeInstanceException exception) {
				break;
			}
		}
		
		for (int i = 0; i <2; i ++)
			try {
				pool.releaseReusable(new Reusable());
			} catch (DuplicatedInstanceException e) {}
	}

	/**
	 * Test que comprueba que salta la excepcion NotFreeInstanceException cuando se intenta sacar
	 * un elemento de la pool y esta esta vacia.
	 */
	@Test
	public void NotFreeInstanceTest(){
		int contador = 0;
		
		while(true) {
			try {
				pool.acquireReusable();
				contador++;
			}catch (NotFreeInstanceException exception) {
				assertTrue(exception.getMessage().equals("No hay mas instancias reutilizables disponibles. Reintentalo mas tarde"));
				assertEquals(contador, 2);
				break;
			}
		}
	}

}