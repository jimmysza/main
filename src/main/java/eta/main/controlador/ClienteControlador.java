package eta.main.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String cliente(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("cliente", new Cliente()); // Para el formulario
        return "cliente";
    }

    @PostMapping
    public String guardaCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {
        if (cliente.getPersona() == null) {
            model.addAttribute("error", "Debe ingresar los datos de la persona.");
            return "cliente";
        }

        Roles rolPorDefecto = rolesRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        cliente.getPersona().setRol(rolPorDefecto);
        clienteRepository.save(cliente);

        return "redirect:/cliente";
    }

    @PostMapping("/eliminar")
    public String eliminarCliente(@RequestParam("idCliente") Long idCliente, Model model) {
        if (!clienteRepository.existsById(idCliente)) {
            model.addAttribute("error", "El cliente con ID " + idCliente + " no existe.");
            return "cliente";
        }

        clienteRepository.deleteById(idCliente);
        return "redirect:/cliente";
    }
}
