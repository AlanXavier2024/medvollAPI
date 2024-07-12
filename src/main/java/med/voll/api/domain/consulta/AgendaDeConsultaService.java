package med.voll.api.domain.consulta;


import med.voll.api.domain.consulta.validaciones.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;

    @Autowired
    List<ValidadorCancelamientoDeConsulta>validadorCancelamientoDeConsultas;

    public  DatosDetalleConsulta agendar(DatosAgendarConsulta datos) {

        if (!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("este id para el paciente no fue encontrado");
        }
        if(datos.idMedico()!=null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrado");
        }
        validadores.forEach(validador -> validador.validar(datos));
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var medico = seleccionarMedico(datos);
        if (medico==null){
            throw new ValidacionDeIntegridad("no existen medicos disponibles para este horario y especialidad");
        }

        var consulta = new Consulta(medico, paciente, datos.fechaConsulta());
        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    public void cancelar(DatosCancelamientoConsulta datos){
        if(!consultaRepository.existsById(datos.idConsulta())){
            throw new ValidacionDeIntegridad("id de la consulta no existe");
        }

        validadorCancelamientoDeConsultas.forEach(validador -> validador.validar(datos));

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());


    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if (datos.idMedico()!= null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad()==null){
            throw new ValidacionDeIntegridad("debe selecionarse una especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fechaConsulta());
    }
}
