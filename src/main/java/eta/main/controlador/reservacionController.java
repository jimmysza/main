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

import eta.main.modeloEntidad.Actividad;
import eta.main.modeloEntidad.Admin;
import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Fechas;
import eta.main.modeloEntidad.Plan;
import eta.main.modeloEntidad.Reservacion;
import eta.main.repositorio.ActividadRepository;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.FechasRepository;
import eta.main.repositorio.PlanRepository;
import eta.main.repositorio.ReservacionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reservacion")
public class reservacionController {

    @Autowired
    private FechasRepository fechasRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReservacionRepository reservacionRepository;

    @Autowired
    private PlanRepository planRepository;

    
    @GetMapping
    public String mostrarReservacion(HttpSession session, Model model) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");

        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        Long idAdmin = adminLogueado.getIdAdmin();

        model.addAttribute("ReservacionEntidad", new Reservacion());

        model.addAttribute("listaFechas", fechasRepository.findAll());

        model.addAttribute("listaActividades", actividadRepository.findAll());

        model.addAttribute("listaClientes", clienteRepository.findAll());

        model.addAttribute("ListaPlanes", planRepository.findAll());

        model.addAttribute("ListaReservacion", reservacionRepository.findAll());

        model.addAttribute("CantidadReservaciones", reservacionRepository.count());

        model.addAttribute("adminLogueado", adminLogueado);

        return "bd/reservacion";
    }

    @PostMapping
    public String guardarReservacion(@ModelAttribute("ReservacionEntidad") Reservacion reservacionEntidad, HttpSession session, Model model, HttpServletRequest request) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        Long idActividad = Long.valueOf(request.getParameter("actividad.idActividad"));
        Long idFecha = Long.valueOf(request.getParameter("fecha.idFecha"));
        Long idPlan = Long.valueOf(request.getParameter("plan.idPlan"));
        Long idCliente = Long.valueOf(request.getParameter("cliente.idCliente"));

        Optional<Actividad> actividadOpt = actividadRepository.findById(idActividad);
        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        Optional<Fechas> fechaOpt = fechasRepository.findById(idFecha);
        Optional<Plan> PlanOpt = planRepository.findById(idPlan);

        if (actividadOpt.isPresent() && clienteOpt.isPresent() && fechaOpt.isPresent()) {
            Reservacion reservacionNueva = new Reservacion();
            reservacionNueva.setActividad(actividadOpt.get());
            reservacionNueva.setCliente(clienteOpt.get());
            reservacionNueva.setFecha(fechaOpt.get());
            reservacionNueva.setPlan(PlanOpt.get());
            reservacionRepository.save(reservacionNueva);
        } else {
            model.addAttribute("error", "Actividad, Cliente o Fecha no encontrados.");
        }

        return "redirect:/reservacion";
    }

    @GetMapping("/eliminar/{idReservacion}")
    public String eliminarReservacion(@PathVariable Long idReservacion) {
        reservacionRepository.deleteById(idReservacion);

        return "redirect:/reservacion";
    }

    @GetMapping("/editar/{idReservacion}")
    @ResponseBody
    public Reservacion obtenerReservacionesParaEdicion(@PathVariable("id") Long id) {
        return reservacionRepository.findById(id).orElse(null);
    }

    @GetMapping("/editar-form/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model, HttpSession session) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        Reservacion reservacion = reservacionRepository.findById(id).orElse(new Reservacion());
        model.addAttribute("ReservacionEntidad", reservacion);
        // Agrega también los atributos necesarios para los selects:
        model.addAttribute("listaActividades", actividadRepository.findAll());
        model.addAttribute("listaFechas", fechasRepository.findAll());
        model.addAttribute("ListaPlanes", planRepository.findAll());
        model.addAttribute("listaClientes", clienteRepository.findAll());

        System.out.println("Reservacion a editar: " + reservacion.getIdReservacion());
        if (reservacion.getFecha() != null) {
            System.out.println("Fecha seleccionada: " + reservacion.getFecha().getDia());
        }
        if (reservacion.getPlan() != null) {
            System.out.println("Plan seleccionado: " + reservacion.getPlan().getTitulo());
        }

        return "bd/edits/reservacionMain-editar";
    }

    @PostMapping("/actualizar")
    public String actualizarReservacion(@ModelAttribute("ReservacionEntidad") Reservacion reservacionEntidad, HttpSession session, Model model) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        Optional<Reservacion> reservacionOpt = reservacionRepository.findById(reservacionEntidad.getIdReservacion());
        if (reservacionOpt.isPresent()) {
            Reservacion reservacionExistente = reservacionOpt.get();

            // Buscar entidades completas por ID
            Optional<Actividad> actividadOpt = actividadRepository.findById(reservacionEntidad.getActividad().getIdActividad());
            Optional<Cliente> clienteOpt = clienteRepository.findById(reservacionEntidad.getCliente().getIdCliente());
            Optional<Fechas> fechaOpt = fechasRepository.findById(reservacionEntidad.getFecha().getIdFecha());
            Optional<Plan> planOpt = planRepository.findById(reservacionEntidad.getPlan().getIdPlan());

            // Asignar entidades completas
            actividadOpt.ifPresent(reservacionExistente::setActividad);
            clienteOpt.ifPresent(reservacionExistente::setCliente);
            fechaOpt.ifPresent(reservacionExistente::setFecha);
            planOpt.ifPresent(reservacionExistente::setPlan);

            reservacionRepository.save(reservacionExistente);
        } else {
            model.addAttribute("error", "Reservación no encontrada.");
            return "bd/reservacion";
        }

        return "redirect:/reservacion";
    }


}
