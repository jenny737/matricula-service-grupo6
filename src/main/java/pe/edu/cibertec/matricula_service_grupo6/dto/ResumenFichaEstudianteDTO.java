package pe.edu.cibertec.matricula_service_grupo6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumenFichaEstudianteDTO {
    private Long estudianteId;
    private String nombreEstudiante;
    private String carrera;
    private String sede;
    private Integer totalMatriculasRealizadas;
    private Double promedioPonderadoAcumulado;
    private List<MatriculaResponseDTO> historialMatriculas;
    private List<CursoDisponibilidadDTO> catalogoCursosElegibles;
    private String situacionAcademica; // Ej: 'REGULAR', 'OBSERVADO'
}