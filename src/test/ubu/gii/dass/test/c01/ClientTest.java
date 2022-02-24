package test.ubu.gii.dass.test.c01;

import org.junit.Test;

import main.ubu.gii.dass.c01.Client;

/**
 * @author Ivan Cortes Aliende
 * @author Alberto Diez Busto
 *
 */
public class ClientTest {
	/**
	 * Test que comprueba que se creen correctamente los objetos de la clase Client
	 */
	@Test
	public void ClientClassTest(){
			Client cliente = new Client();
			assert(cliente instanceof Client);
		}

}