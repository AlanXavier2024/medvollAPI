package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DatosAgendarConsulta(Long idPaciente, Long idMedico, @NotNull @Future LocalDateTime fechaConsulta, Especialidad especialidad){
}
