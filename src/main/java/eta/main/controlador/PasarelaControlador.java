package eta.main.controlador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Fechas;
import eta.main.modeloEntidad.Reservacion;
import eta.main.repositorio.ActividadRepository;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.FechasRepository;
import eta.main.repositorio.PlanRepository;
import eta.main.repositorio.ReservacionRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class PasarelaControlador {

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

    @GetMapping("/pasarela/{idPlan}")
    public String mostrarPasarela(@PathVariable Long idPlan, HttpSession session, Model model) {
        Cliente clienteLogueado = (Cliente) session.getAttribute("clienteLogueado");

        if (clienteLogueado == null) {
            return "redirect:/ingreso/cliente";
        }

        Long idActividad = (Long) session.getAttribute("idActividad");

        // Carga los objetos completos
        var actividad = actividadRepository.findById(idActividad).orElse(null);
        var plan = planRepository.findById(idPlan).orElse(null);

        session.setAttribute("idPlan", idPlan);
        model.addAttribute("idPlan", idPlan);
        model.addAttribute("idActividad", idActividad);

        model.addAttribute("ReservacionEntidad", new Reservacion());
        model.addAttribute("listaFechas", fechasRepository.findByActividad_IdActividad(idActividad));
        model.addAttribute("clienteLogueado", clienteLogueado);

        // Agrega los objetos completos
        model.addAttribute("actividad", actividad);
        model.addAttribute("plan", plan);

        return "bd/pasarelaPago";
    }

    @PostMapping("/pasarela")
    public String procesarReservacion(
            @ModelAttribute("ReservacionEntidad") Reservacion reservacion,
            HttpSession session,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {

        Long idPlan = (Long) session.getAttribute("idPlan");
        Long idActividad = (Long) session.getAttribute("idActividad");
        Long idCliente = ((Cliente) session.getAttribute("clienteLogueado")).getIdCliente();

        // Validar que se haya seleccionado una fecha
        if (reservacion.getFecha() == null || reservacion.getFecha().getIdFecha() == null) {
            redirectAttributes.addFlashAttribute("mensaje", "Debes seleccionar una fecha.");
            return "redirect:/pasarela/" + idPlan;
        }

        Optional<Fechas> fechaOpt = fechasRepository.findById(reservacion.getFecha().getIdFecha());

        if (fechaOpt.isPresent()) {
            reservacion.setPlan(planRepository.findById(idPlan).orElse(null));
            reservacion.setActividad(actividadRepository.findById(idActividad).orElse(null));
            reservacion.setCliente(clienteRepository.findById(idCliente).orElse(null));
            reservacion.setFecha(fechaOpt.get());
            reservacionRepository.save(reservacion);
            redirectAttributes.addFlashAttribute("mensaje", "¡Pago realizado con éxito!");
            return "redirect:/pasarela/" + idPlan + "?success";
        }
        redirectAttributes.addFlashAttribute("mensaje", "Error al guardar la reservación.");
        return "redirect:/pasarela/" + idPlan;
    }

}


