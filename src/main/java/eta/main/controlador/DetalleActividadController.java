package eta.main.controlador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import eta.main.modeloEntidad.Actividad;
import eta.main.repositorio.ActividadRepository;
import eta.main.repositorio.PlanRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/detalle")
public class DetalleActividadController {

    @Autowired
    private ActividadRepository actividadRepository;

    @Autowired
    private PlanRepository planRepository;

    @GetMapping("/actividad/{id}")
    public String detalleActividadIndice(@PathVariable Long id, Model model, HttpSession session) {

        session.setAttribute("idActividad", id);


        
        // Busca la actividad en la base de datos usando el ID proporcionado en la URL.
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada: " + id));

        // Obtiene solo los planes de esa actividad
        model.addAttribute("ListaPlanes", planRepository.findByActividad_IdActividad(id));

        // Agrega la entidad actividad completa al modelo para acceder a sus datos desde el HTML.
        model.addAttribute("actividadEntidad", actividad);

        // Devuelve el nombre de la vista (HTML) que se va a renderizar para mostrar el detalle de la actividad.
        return "detalle_actividad";
    }

}
