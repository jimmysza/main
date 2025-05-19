package eta.main.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eta.main.modeloEntidad.Admin;
import eta.main.repositorio.ActividadRepository;
import eta.main.repositorio.AdminRepository;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.ColaboradorRepository;
import eta.main.repositorio.PersonaRepository;
import eta.main.repositorio.PlanRepository;
import eta.main.repositorio.ReservacionRepository;
import eta.main.repositorio.RolesRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/main")
public class MainBdController {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ReservacionRepository reservacionRepository;

    @Autowired
    private RolesRepository rolesRepository;


    @GetMapping
    public String MostrarMainBd(Model model, HttpSession session ) {
        model.addAttribute("CantidadCliente", clienteRepository.count());
        model.addAttribute("CantidadColaborador", colaboradorRepository.count());
        model.addAttribute("CantidadPersona", personaRepository.count());
        model.addAttribute("CantidadPlan", planRepository.count());
        model.addAttribute("CantidadReservacion", reservacionRepository.count());
        model.addAttribute("CantidadRoles", rolesRepository.count());
        model.addAttribute("CantidadActividad", actividadRepository.count());

        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }
        model.addAttribute("adminLogueado", adminLogueado); // <-- Así lo usas en la vista

        return "bd/mainBd";
    }
}