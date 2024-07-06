package education.next.oracle.LiterAluraChallenge;

import education.next.oracle.LiterAluraChallenge.menu.Menu;
import education.next.oracle.LiterAluraChallenge.repository.LivroRepository;
import education.next.oracle.LiterAluraChallenge.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraChallengeApplication implements CommandLineRunner {
	@Autowired
	LivroRepository livroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var menu = new Menu(new LivroService(livroRepository));
		menu.iniciar();
	}
}
