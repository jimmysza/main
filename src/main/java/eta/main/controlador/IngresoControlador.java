package eta.main.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Persona;
import eta.main.modeloEntidad.Roles;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.RolesRepository;

@Controller
@RequestMapping("/ingreso")
public class IngresoControlador {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public String guardaCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {
        if (cliente.getPersona() == null) {
            model.addAttribute("error", "Debe ingresar los datos de la persona.");
            return "indice";
        }

        Roles rolPorDefecto = rolesRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        cliente.getPersona().setRoles(rolPorDefecto);
        clienteRepository.save(cliente);

        return "redirect:/indice";
    }
}
