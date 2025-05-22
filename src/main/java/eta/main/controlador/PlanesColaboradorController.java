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
import eta.main.modeloEntidad.Colaborador;
import eta.main.modeloEntidad.Plan;
import eta.main.repositorio.ActividadRepository;
import eta.main.repositorio.ColaboradorRepository;
import eta.main.repositorio.PlanRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/PlanesColaborador")
public class PlanesColaboradorController {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private PlanRepository planRepository;

    private void cargarPlanes(HttpSession session, Model model) {
        Colaborador colaborador = (Colaborador) session.getAttribute("ColaboradorLogueado");
        
        if (colaborador != null) {
            Long idColaborador = colaborador.getIdColaborador();
            model.addAttribute("ListaPLanes", planRepository.findByActividad_Colaborador_IdColaborador(idColaborador));
            model.addAttribute("ListaActividad", actividadRepository.findByColaborador_IdColaborador(idColaborador));
            model.addAttribute("CantidadPlanes", planRepository.countByActividadColaboradorIdColaborador(idColaborador));
        } else {
            model.addAttribute("ListaPLanes", null);
            model.addAttribute("CantidadPlanes", 0L);
        }
    }

    @GetMapping
    public String verPlanesColaboradores(HttpSession session, Model model) {
        
        Colaborador colaborador = (Colaborador) session.getAttribute
        ("ColaboradorLogueado");

        if (colaborador == null) {
            return "redirect:/ingreso/colaborador";
        }

        Long idColaborador = colaborador.getIdColaborador();
        model.addAttribute("planEntidad", new Plan());
        model.addAttribute("listaPlanes", planRepository.findByActividad_Colaborador_IdColaborador(idColaborador));
        model.addAttribute("listaActividades", actividadRepository.findByColaborador_IdColaborador(idColaborador));
        model.addAttribute("CantidadPlanes", planRepository.countByActividadColaboradorIdColaborador(idColaborador));
        model.addAttribute("colaboradorLogueado", colaborador);
        return "bd/colaboradorPlanes";
    }

    @PostMapping
    public String guardarPLanes(HttpServletRequest request, Model model, HttpSession session) {
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
            cargarPlanes(session, model);
            return "bd/colaboradorPlanes";
        }

        return "redirect:/PlanesColaborador";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPlanes(@PathVariable("id") Long idPLan) {
        Optional<Plan> PlandOpt = planRepository.findById(idPLan);
        if (PlandOpt.isPresent()) {
            Plan planEli = PlandOpt.get();
            planRepository.delete(planEli);
            return "redirect:/PlanesColaborador";
        } else {
            return "redirect:/PlanesColaborador?error=notfound";
        }
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Plan obtenerPlanesParaEdicion(@PathVariable("id") Long id) {
        return planRepository.findById(id).orElse(null);
    }

    @GetMapping("/editarColabPlanes-form/{id}")
    public String mostrarFormularioEdicionColabPlan(@PathVariable("id") Long id, Model model) {
        Plan plan = planRepository.findById(id).orElse(null);
        
        model.addAttribute("planEntidad", plan);
        model.addAttribute("listaColaboradores", colaboradorRepository.findAll());
        model.addAttribute("listaActividades", actividadRepository.findAll());
        return "bd/edits/planesColab-editar";
    }

    @PostMapping("/editar")
    public String actualizarPlan(HttpServletRequest request, Model model, HttpSession session) {
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
            cargarPlanes(session, model);
            return "bd/colaboradorPlanes";
        }
        return "redirect:/PlanesColaborador";
    }
}
