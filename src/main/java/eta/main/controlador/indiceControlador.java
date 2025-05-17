package eta.main.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import eta.main.modeloEntidad.Actividad;
import eta.main.modeloEntidad.Cliente;
import eta.main.repositorio.ActividadRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/indice")
public class indiceControlador {

    @Autowired
    private ActividadRepository actividadRepository;

    // ...existing code...
    @GetMapping
    public String getIndice(Model model, HttpSession session) {
        Actividad nuevaActividad = new Actividad();
        model.addAttribute("actividadEntidad", nuevaActividad);
        model.addAttribute("actividades", actividadRepository.findAll());

        // Extraer cliente desde la sesión
        Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");
        if (cliente != null) {
            if (cliente.getPersona() != null) {
                String nombreCompleto = cliente.getPersona().getNombre();
                model.addAttribute("nombreUsuario", nombreCompleto);
            } else {
                model.addAttribute("nombreUsuario", cliente.getUsuario());
            }
        } else {
            model.addAttribute("nombreUsuario", "Invitado");
        }

        return "indice";
    }
// ...existing code...

    @GetMapping("/transporte")
    public String MostrarTransporte() {
        return "TransportePlaya";
    }

    @GetMapping("/actividad/{id}")
    public String detalleActividad(@PathVariable Long id, Model model) {

        // Busca la actividad en la base de datos usando el ID proporcionado en la URL.
        // Si no la encuentra, lanza una excepción indicando que no se encontró la actividad.
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada: " + id));

        // Agrega la entidad actividad completa al modelo para acceder a sus datos desde el HTML.
        model.addAttribute("actividadEntidad", actividad);

        // Devuelve el nombre de la vista (HTML) que se va a renderizar para mostrar el detalle de la actividad.
        return "detalle_actividad"; // Este es el archivo HTML que debe existir en templates/
    }

}
