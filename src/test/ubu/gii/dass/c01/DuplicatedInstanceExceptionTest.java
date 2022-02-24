package test.ubu.gii.dass.test.c01;

import static org.junit.Assert.assertTrue;
import org.junit.*;
import main.ubu.gii.dass.c01.DuplicatedInstanceException;
import main.ubu.gii.dass.c01.NotFreeInstanceException;
import main.ubu.gii.dass.c01.Reusable;
import main.ubu.gii.dass.c01.ReusablePool;


/**
 * @author Iván Cortés Aliende
 * @author Alberto Diez Busto
 *
 */
public class DuplicatedInstanceExceptionTest {

	private ReusablePool pool;
	private Reusable reusable;

	@Before
	public void before() throws Exception {
		pool = ReusablePool.getInstance();
		reusable = new Reusable();
	}

	@After
	public void after() throws Exception {
		while(true) {
			try {
				pool.acquireReusable();
			}catch (NotFreeInstanceException exception) {
				break;
			}
		}
	}

	/**
	 * Test que comprueba que salte la excepcion DuplicatedInstanceTest en el caso
	 * de querer introducir un elemento ya introducido en la pool.
	 */
	@Test
	public void DuplicatedInstanceTest(){
		boolean detectado = false;
		
		try {
			pool.releaseReusable(reusable);
		} catch (DuplicatedInstanceException exception) {
			assertTrue(false);
		}
		
		try {
			pool.releaseReusable(reusable);
		} catch (DuplicatedInstanceException exception) {
			detectado = true;
			assertTrue(exception.getMessage().equals("Ya existe esa instancia en el pool."));
		}
		
		assertTrue(detectado);
	}

}

