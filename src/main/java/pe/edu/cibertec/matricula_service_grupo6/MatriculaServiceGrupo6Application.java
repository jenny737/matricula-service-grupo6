package pe.edu.cibertec.matricula_service_grupo6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MatriculaServiceGrupo6Application {

	public static void main(String[] args) {
		SpringApplication.run(MatriculaServiceGrupo6Application.class, args);
	}

}
