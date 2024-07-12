package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class HorarioDeFuncionamiento implements ValidadorDeConsultas{
    public void validar(DatosAgendarConsulta datos) {
        var domingo = DayOfWeek.SUNDAY.equals(datos.fechaConsulta().getDayOfWeek());

        var antesdDeApertura = datos.fechaConsulta().getHour() < 7;
        var despuesDeCierre = datos.fechaConsulta().getHour() > 19;
        if (domingo || antesdDeApertura || despuesDeCierre) {
            throw new ValidationException("El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");

        }
    }
}
