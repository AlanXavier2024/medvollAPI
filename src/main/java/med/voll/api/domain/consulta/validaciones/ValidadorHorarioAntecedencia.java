package med.voll.api.domain.consulta.validaciones;


import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorCancelamientoDeConsulta{

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosCancelamientoConsulta datos) {
        var consulta = repository.getReferenceById(datos.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaHoras = Duration.between(ahora, consulta.getData()).toHours();

        if (diferenciaHoras < 24) {
            throw new ValidationException("Consulta solo puede ser cancelada con minimo 24 horas de anticipacion");
        }
    }
}
