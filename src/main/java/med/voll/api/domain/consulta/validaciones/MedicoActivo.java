package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorDeConsultas{
    @Autowired
    private MedicoRepository repository;

    public void validar(DatosAgendarConsulta datos){

        if (datos.idMedico()==null){
            return;
        }
        var medicoActivo = repository.findActivoById(datos.idMedico());

        if (!medicoActivo){
            throw new RuntimeException("No se permite agendar citas a medicos inactivos en el sistema");
        }

    }
}
