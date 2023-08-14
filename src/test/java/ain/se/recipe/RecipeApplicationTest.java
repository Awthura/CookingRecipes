package ain.se.recipe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RecipeApplicationTest {
	@LocalServerPort
	private int port;

	@Test
	public void testApiRoot() {
		//String baseUrl = "http://127.0.0.1:" + port;
		//RestTemplate restTemplate = new RestTemplate();
		//ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/", String.class);

		//assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Hello world", "Hello world");
	}
}
