package eta.main.controlador;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eta.main.modeloEntidad.Actividad;
import eta.main.modeloEntidad.Admin;
import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Plan;
import eta.main.repositorio.ActividadRepository;
import eta.main.repositorio.ColaboradorRepository;
import eta.main.repositorio.PlanRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/planes")
public class PlanesController {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @GetMapping
    public String listarPlanes(HttpSession session, Model model) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        model.addAttribute("planEntidad", new Plan());
        model.addAttribute("listaPlanes", planRepository.findAll());
        model.addAttribute("listaColaboradores", colaboradorRepository.findAll());
        model.addAttribute("listaActividades", actividadRepository.findAll());
        model.addAttribute("CantidadPlanes", planRepository.count());
        model.addAttribute("adminLogueado", adminLogueado);
        return "bd/planes";
    }

    @PostMapping
    public String guardarPlan(HttpServletRequest request, Model model, HttpSession session) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        BigDecimal precio = new BigDecimal(request.getParameter("precio"));
        Long idActividad = Long.valueOf(request.getParameter("idActividad"));

        Optional<Actividad> actividadOpt = actividadRepository.findById(idActividad);

        if (actividadOpt.isPresent()) {
            Plan plan = new Plan();
            plan.setTitulo(titulo);
            plan.setDescripcion(descripcion);
            plan.setPrecio(precio);
            plan.setActividad(actividadOpt.get());
            planRepository.save(plan);
        } else {
            model.addAttribute("error", "Actividad no encontrada.");
        }
        return "redirect:/planes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPlan(@PathVariable("id") Long idPlan, HttpSession session) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }
        planRepository.deleteById(idPlan);
        return "redirect:/planes";
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Plan obtenerPlanesParaEdicion(@PathVariable("id") Long id) {
        return planRepository.findById(id).orElse(null);
    }

    @GetMapping("/editar-form/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model, HttpSession session) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }
        Plan plan = planRepository.findById(id).orElse(null);
        model.addAttribute("planEntidad", plan);
        model.addAttribute("listaActividades", actividadRepository.findAll());
        model.addAttribute("adminLogueado", adminLogueado);
        return "bd/edits/planes-editar";
    }

    @PostMapping("/editar")
    public String actualizarPlan(HttpServletRequest request, Model model, HttpSession session) {
        
        
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        Long idPlan = Long.valueOf(request.getParameter("idPlan"));
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        BigDecimal precio = new BigDecimal(request.getParameter("precio"));
        Long idActividad = Long.valueOf(request.getParameter("idActividad"));

        Optional<Plan> planOpt = planRepository.findById(idPlan);
        Optional<Actividad> actividadOpt = actividadRepository.findById(idActividad);

        if (planOpt.isPresent() && actividadOpt.isPresent()) {
            Plan plan = planOpt.get();
            plan.setTitulo(titulo);
            plan.setDescripcion(descripcion);
            plan.setPrecio(precio);
            plan.setActividad(actividadOpt.get());
            planRepository.save(plan);
        } else {
            model.addAttribute("error", "Datos inválidos para editar el plan.");
        }
        return "redirect:/planes";
    }
}
