package eta.main.controlador;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Persona;
import eta.main.repositorio.ClienteRepository;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/ingreso")
public class IngresoControlador {

    @Autowired
    private ClienteRepository clienteRepository;


    @GetMapping
    public String getIngreso(Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
            model.addAttribute("persona", new Persona());
        }
        return "ingreso";
    }

    

@PostMapping
public String IniciarSesion(Cliente cliente, Model model, HttpSession session) {
    Cliente clienteExistente = clienteRepository.findByUsuarioAndContrasena(
            cliente.getUsuario(),
            cliente.getContrasena()
    );

    if (clienteExistente != null) {
        session.setAttribute("usuarioLogueado", clienteExistente);
        session.setAttribute("ultimoIngreso", LocalDateTime.now()); // Guarda el último ingreso en sesión
        return "redirect:/indice";
    } else {
        model.addAttribute("cliente", cliente);
        model.addAttribute("ContraseñaIncorrecta", "La Contraseña o Usuario es Incorrecto");
        return "ingreso";
    }
}

}