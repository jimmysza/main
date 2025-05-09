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
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public String cliente(Model model) {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setPersona(new Persona()); // <- Esto evita el null
        model.addAttribute("cliente", nuevoCliente); // Para el formulario
        model.addAttribute("clientes", clienteRepository.findAll());
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

        cliente.getPersona().setRoles(rolPorDefecto);
        clienteRepository.save(cliente);

        return "redirect:/cliente";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Long idCliente) {
        if (clienteRepository.existsById(idCliente)) {
            clienteRepository.deleteById(idCliente);
            return "redirect:/cliente";
        } else {
            return "redirect:/cliente?error=deleteFailed";
        }
    }

    @PostMapping("/editar")  // Change from @PatchMapping to @PostMapping
    public String editarClienteParcial(@ModelAttribute("cliente") Cliente cliente, Model model) {
        // Rest of the method remains the same
        if (cliente.getIdCliente() == null || !clienteRepository.existsById(cliente.getIdCliente())) {
            model.addAttribute("error", "El cliente con ID proporcionado no existe.");
            return "redirect:/cliente";
        }

        Cliente clienteExistente = clienteRepository.findById(cliente.getIdCliente()).orElseThrow();
        Roles rolPorDefecto = rolesRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Actualiza solo si los campos vienen con valores
        if (cliente.getPersona() != null) {
            if (cliente.getPersona().getNombre() != null && !cliente.getPersona().getNombre().isBlank()) {
                clienteExistente.getPersona().setNombre(cliente.getPersona().getNombre());
            }
        
            if (cliente.getPersona().getCorreoElectronico() != null && !cliente.getPersona().getCorreoElectronico().isBlank()) {
                clienteExistente.getPersona().setCorreoElectronico(cliente.getPersona().getCorreoElectronico());
            }

            // Siempre se asegura que el rol esté presente
            clienteExistente.getPersona().setRoles(rolPorDefecto);
        }


        clienteRepository.save(clienteExistente);
        return "redirect:/cliente";
    }

}
