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

    @GetMapping
    public String getIndice(Model model, HttpSession session) {
        Actividad nuevaActividad = new Actividad();
        model.addAttribute("actividadEntidad", nuevaActividad);
        model.addAttribute("actividades", actividadRepository.findAll());

        // Extraer cliente y su información desde la sesión
        Object cliente = session.getAttribute("usuarioLogueado");

        if (cliente != null) {
            Cliente clienteObj = (Cliente) cliente;
            if (clienteObj.getPersona() != null) {
                // Concatenar nombre y apellido de la persona
                String nombreCompleto =  " " + clienteObj.getPersona().getNombre();
                model.addAttribute("nombreUsuario", nombreCompleto);
            } else {
                model.addAttribute("nombreUsuario", " " + clienteObj.getUsuario()); // Fallback si no tiene persona asociada
            }
        }

        return "indice";
    }

    @GetMapping("/transporte")
    public String MostrarTransporte(){
        return"TransportePlaya";
    }
}
