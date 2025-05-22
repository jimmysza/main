package eta.main.controlador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;//Se usa para pasar datos del backend al frontend (por ejemplo, una lista de clientes que se mostrará en una vista HTML).
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; //Vincula datos del formulario a un objeto.
import org.springframework.web.bind.annotation.PathVariable;//extrae el variables de la URL
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;//Indica que el valor retornado se envía directamente como respuesta HTTP 

import eta.main.modeloEntidad.Admin;
import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Persona;
import eta.main.modeloEntidad.Roles;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.PersonaRepository;
import eta.main.repositorio.RolesRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/clientePerfil")
public class clientePerfilControlador {
    
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping
    public String mostrarPerfil(HttpSession session, Model model) {
        Cliente clienteLogueado = (Cliente) session.getAttribute("clienteLogueado");

        if (clienteLogueado == null) {
            return "redirect:/ingreso/cliente";
        }

        Long idCliente = clienteLogueado.getIdCliente();
        model.addAttribute("cliente", clienteRepository.findById(idCliente).orElse(null));
        
        return "bd/profile";
    }
}
