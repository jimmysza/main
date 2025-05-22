package eta.main.controlador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Fechas;
import eta.main.modeloEntidad.Plan;
import eta.main.modeloEntidad.Reservacion;
import eta.main.repositorio.ActividadRepository;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.FechasRepository;
import eta.main.repositorio.PersonaRepository;
import eta.main.repositorio.PlanRepository;
import eta.main.repositorio.ReservacionRepository;
import eta.main.repositorio.RolesRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/clientePerfil")

public class clientePefil {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private FechasRepository fechasRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private ReservacionRepository reservacionRepository;

    @Autowired
    private PlanRepository planRepository;

    @GetMapping
    public String getClientePerfil(HttpSession session, Model model) {

        Cliente clienteLogueado = (Cliente) session.getAttribute("clienteLogueado");

        if (clienteLogueado == null) {
            return "redirect:/ingreso/cliente";
        }

        Long idCliente = (Long) clienteLogueado.getIdCliente();
        model.addAttribute("clienteLogueado", clienteLogueado);
        model.addAttribute("cliente", clienteRepository.findById(idCliente).orElse(null));
        model.addAttribute("reservaciones", reservacionRepository.findByClienteIdCliente(idCliente));

        return "clientePerfil";
    }

    @GetMapping("/eliminar/{idReservacion}")
    public String eliminarReservacion(@PathVariable Long idReservacion) {
        reservacionRepository.deleteById(idReservacion);

        return "redirect:/clientePerfil";
    }

    @GetMapping("/editar/{idReservacion}")
    @ResponseBody
    public Reservacion obtenerReservacionesParaEdicion(@PathVariable("id") Long id) {
        return reservacionRepository.findById(id).orElse(null);
    }

    @GetMapping("/editar-form/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model, HttpSession session) {
        Cliente clienteLogueado = (Cliente) session.getAttribute("clienteLogueado");

        if (clienteLogueado == null) {
            return "redirect:/ingreso/cliente";
        }

        Reservacion reservacion = reservacionRepository.findById(id).orElse(new Reservacion());
        model.addAttribute("ReservacionEntidad", reservacion);
        // Agrega también los atributos necesarios para los selects:
        model.addAttribute("listaActividades", actividadRepository.findAll());
        model.addAttribute("listaFechas", fechasRepository.findByActividad_IdActividad(reservacion.getActividad().getIdActividad()));
        model.addAttribute("ListaPlanes", planRepository.findByActividad_IdActividad(reservacion.getActividad().getIdActividad()));
        model.addAttribute("listaClientes", clienteRepository.findAll());

        System.out.println("Reservacion a editar: " + reservacion.getIdReservacion());
        if (reservacion.getFecha() != null) {
            System.out.println("Fecha seleccionada: " + reservacion.getFecha().getDia());
        }
        if (reservacion.getPlan() != null) {
            System.out.println("Plan seleccionado: " + reservacion.getPlan().getTitulo());
        }

        return "bd/edits/perfil-edit";
    }

    @PostMapping("/actualizar")
    public String actualizarReservacion(@ModelAttribute("ReservacionEntidad") Reservacion reservacionEntidad, HttpSession session, Model model) {
        Cliente clienteLogueado = (Cliente) session.getAttribute("clienteLogueado");
        if (clienteLogueado == null) {
            return "redirect:/ingreso/cliente";
        }
        Optional<Reservacion> reservacionOpt = reservacionRepository.findById(reservacionEntidad.getIdReservacion());
        if (reservacionOpt.isPresent()) {
            Reservacion reservacionExistente = reservacionOpt.get();

            // Solo actualizar fecha y plan
            Optional<Fechas> fechaOpt = fechasRepository.findById(reservacionEntidad.getFecha().getIdFecha());
            Optional<Plan> planOpt = planRepository.findById(reservacionEntidad.getPlan().getIdPlan());

            fechaOpt.ifPresent(reservacionExistente::setFecha);
            planOpt.ifPresent(reservacionExistente::setPlan);

            reservacionRepository.save(reservacionExistente);
        } else {
            model.addAttribute("error", "Reservación no encontrada.");
            return "bd/clientePerfil";
        }
        return "redirect:/clientePerfil";
    }
}
