    package eta.main.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        model.addAttribute("clienteLogueado", cliente);
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
        return "transporte";
    }

    @GetMapping("/playa")
    public String MostrarPlaya() {
        return "playa";
    }
    

}
