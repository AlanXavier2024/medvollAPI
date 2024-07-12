package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{
    @Autowired
    private ConsultaRepository repository;
    public  void validar (DatosAgendarConsulta datos){
        var primerHorario = datos.fechaConsulta().withHour(7);
        var ultimoHorario = datos.fechaConsulta().withHour(18);

        var pacienteConConsulta = repository.existsByPacienteIdAndDataBetween(datos.idPaciente(), primerHorario,ultimoHorario);

        if (pacienteConConsulta){
            throw new ValidationException("Paciente ya tiene una consulta agendada para el dia seleccionado");
        }
    }
}
