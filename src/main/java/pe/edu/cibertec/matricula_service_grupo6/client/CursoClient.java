package pe.edu.cibertec.matricula_service_grupo6.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.cibertec.matricula_service_grupo6.dto.CursoDisponibilidadDTO;

import java.util.List;

@FeignClient(name = "curso-service-grupo6")
public interface CursoClient {

    @GetMapping("/api/v1/cursos/{idCurso}")
    CursoDisponibilidadDTO obtenerCursoPorId(@PathVariable("idCurso") Long idCurso);

    @GetMapping("/api/v1/cursos/estudiante/{estudianteId}")
    List<CursoDisponibilidadDTO> obtenerCursosPorEstudiante(@PathVariable("estudianteId") Long estudianteId);
}