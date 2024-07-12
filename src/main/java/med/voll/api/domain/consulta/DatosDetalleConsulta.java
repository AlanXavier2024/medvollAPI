package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DatosDetalleConsulta(Long id, Long idPaciente, Long idMedico, LocalDateTime fechaConsulta) {
    public DatosDetalleConsulta(Consulta consulta) {
        this(consulta.getId(),consulta.getPaciente().getId(),consulta.getMedico().getId(),consulta.getData());
    }
}
