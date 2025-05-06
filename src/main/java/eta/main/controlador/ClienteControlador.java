package eta.main.controlador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Roles;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.RolesRepository;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
public String index(Model model) {
    model.addAttribute("clientes", clienteRepository.findAll());
    model.addAttribute("cliente", new Cliente());  // Para el formulario en la vista
    return "cliente";  // Redirige a la vista de clientes
}


    @PostMapping
    public String guardaCliente(@ModelAttribute Cliente cliente) {
        Roles rolPorDefecto = rolesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        cliente.getPersona().setRol(rolPorDefecto);
        clienteRepository.save(cliente);
        return "redirect:/cliente"; // Redirige al listado de clientes
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarCliente(@PathVariable Long id, Model model) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isPresent()) {
            model.addAttribute("cliente", clienteOptional.get());
            return "cliente";
        } else {
            return "redirect:/cliente?error=notFound"; // Redirige a la lista con un parámetro de error
        }
    }

    @PostMapping("/eliminar")
    public String deleteCliente(@RequestParam Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return "redirect:/cliente";
        } else {
            return "redirect:/cliente?error=deleteFailed";
        }
    }

}
