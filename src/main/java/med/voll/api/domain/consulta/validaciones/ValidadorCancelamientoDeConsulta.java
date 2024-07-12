package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;

public interface ValidadorCancelamientoDeConsulta {

    public void validar(DatosCancelamientoConsulta datos);
}
