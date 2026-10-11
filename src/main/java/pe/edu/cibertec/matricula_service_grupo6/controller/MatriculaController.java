package pe.edu.cibertec.matricula_service_grupo6.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.matricula_service_grupo6.dto.MatriculaResponseDTO;
import pe.edu.cibertec.matricula_service_grupo6.dto.ResumenFichaEstudianteDTO;
import pe.edu.cibertec.matricula_service_grupo6.service.MatriculaService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/matriculas")
@RequiredArgsConstructor
@RefreshScope
public class MatriculaController {

    private final MatriculaService matriculaService;

    @Value("${edusmart.sede:Sede Simulada Local}")
    private String sede;

    @Value("${edusmart.costo-credito:100.0}")
    private Double costoCredito;

    @Value("${edusmart.max-creditos-ciclo:20}")
    private Integer maxCreditos;

    @Value("${edusmart.mensaje-bienvenida:Bienvenido al sistema temporal}")
    private String mensajeBienvenida;


    @GetMapping("/{idMatricula}")
    public ResponseEntity<MatriculaResponseDTO> obtenerMatricula(@PathVariable Long idMatricula) {
        return ResponseEntity.ok(matriculaService.calcularTotalesMatricula(idMatricula));
    }

    @GetMapping("/validar-y-confirmar/{idMatricula}")
    public ResponseEntity<MatriculaResponseDTO> validarYConfirmarMatricula(@PathVariable Long idMatricula) {
        return ResponseEntity.ok(matriculaService.validarYConfirmar(idMatricula));
    }

    @GetMapping("/ficha-estudiante/{estudianteId}")
    public ResponseEntity<ResumenFichaEstudianteDTO> obtenerFichaEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(matriculaService.generarFicha(estudianteId));
    }


    @GetMapping("/configuracion")
    public ResponseEntity<Map<String, Object>> obtenerConfiguracion() {
        Map<String, Object> config = new HashMap<>();
        config.put("sede", sede);
        config.put("costo-credito", costoCredito);
        config.put("max-creditos-ciclo", maxCreditos);
        config.put("mensaje-bienvenida", mensajeBienvenida);
        return ResponseEntity.ok(config);
    }
}