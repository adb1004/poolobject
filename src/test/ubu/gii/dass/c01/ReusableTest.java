package test.ubu.gii.dass.test.c01;

import org.junit.Test;
import main.ubu.gii.dass.c01.Reusable;

/**
 * @author Iván Cortés Aliende
 * @author Alberto Diez Busto
 *
 */
public class ReusableTest {

	/**
	 * Test que comprueba que los objetos de la clase Reusable se creen correctamente
	 */
	@Test
	public void ReusableClassTest(){
		Reusable reusable = new Reusable();
		assert(reusable instanceof Reusable);
		assert(reusable.util().contains("  :Uso del objeto Reutilizable"));
	}

}