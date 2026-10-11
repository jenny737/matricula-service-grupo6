package pe.edu.cibertec.matricula_service_grupo6.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.matricula_service_grupo6.client.CursoClient;
import pe.edu.cibertec.matricula_service_grupo6.dto.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private final CursoClient cursoClient;

    public MatriculaResponseDTO calcularTotalesMatricula(Long idMatricula) {
        MatriculaResponseDTO matricula = obtenerMatriculaFalsaDeBaseDeDatos(idMatricula);

        int totalCreditos = 0;
        double totalPension = 0.0;

        for (DetalleCursoMatriculadoDTO detalle : matricula.getListaCursosMatriculados()) {
            totalCreditos += detalle.getCreditos();
            totalPension += detalle.getSubtotalCurso();
        }

        matricula.setTotalCreditosInscritos(totalCreditos);
        matricula.setMontoTotalPension(totalPension);

        return matricula;
    }

    public MatriculaResponseDTO validarYConfirmar(Long idMatricula) {
        MatriculaResponseDTO matricula = calcularTotalesMatricula(idMatricula);
        boolean todosTienenVacantes = true;

        for (DetalleCursoMatriculadoDTO detalle : matricula.getListaCursosMatriculados()) {

            CursoDisponibilidadDTO curso = cursoClient.obtenerCursoPorId(detalle.getIdCurso());

            if (curso.getVacantesDisponibles() <= 0 || !"ABIERTO".equalsIgnoreCase(curso.getEstadoCurso())) {
                todosTienenVacantes = false;
                break;
            }
        }

        if (todosTienenVacantes) {
            matricula.setEstadoMatricula("CONFIRMADA");
        } else {
            matricula.setEstadoMatricula("RECHAZADA_SIN_VACANTES");
        }

        return matricula;
    }

    public ResumenFichaEstudianteDTO generarFicha(Long estudianteId) {
        List<MatriculaResponseDTO> historial = List.of(calcularTotalesMatricula(1L));

        List<CursoDisponibilidadDTO> cursosElegibles = cursoClient.obtenerCursosPorEstudiante(estudianteId);

        double promedio = 14.5;
        String situacion = (promedio >= 13.0) ? "REGULAR" : "OBSERVADO";

        return ResumenFichaEstudianteDTO.builder()
                .estudianteId(estudianteId)
                .nombreEstudiante("Gerson Lozano")
                .carrera("Computación e Informática")
                .sede("Campus Lima Central")
                .totalMatriculasRealizadas(historial.size())
                .promedioPonderadoAcumulado(promedio)
                .historialMatriculas(historial)
                .catalogoCursosElegibles(cursosElegibles)
                .situacionAcademica(situacion)
                .build();
    }

    private MatriculaResponseDTO obtenerMatriculaFalsaDeBaseDeDatos(Long id) {
        List<DetalleCursoMatriculadoDTO> detalles = new ArrayList<>();
        detalles.add(DetalleCursoMatriculadoDTO.builder()
                .idCurso(101L).codigoCurso("DAW2").nombreCurso("Desarrollo Web II")
                .seccion("T6DO").creditos(4).costoPorCredito(120.0).subtotalCurso(480.0).build());

        detalles.add(DetalleCursoMatriculadoDTO.builder()
                .idCurso(102L).codigoCurso("BD2").nombreCurso("Base de Datos II")
                .seccion("T6DO").creditos(3).costoPorCredito(120.0).subtotalCurso(360.0).build());

        return MatriculaResponseDTO.builder()
                .idMatricula(id)
                .codigoMatricula("MAT-2026-" + id)
                .fechaMatricula(LocalDateTime.now())
                .estudianteId(1001L)
                .periodoAcademico("2026-II")
                .estadoMatricula("EN_PROCESO")
                .listaCursosMatriculados(detalles)
                .build();
    }
}