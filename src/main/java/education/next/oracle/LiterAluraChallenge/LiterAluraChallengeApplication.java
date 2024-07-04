package education.next.oracle.LiterAluraChallenge;

import education.next.oracle.LiterAluraChallenge.service.ConsumidorDeAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraChallengeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(ConsumidorDeAPI.fazerRequest("https://gutendex.com/books/5432/"));
	}
}
