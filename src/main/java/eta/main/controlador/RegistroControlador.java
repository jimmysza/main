package eta.main.controlador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Roles;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.RolesRepository;

@Controller
@RequestMapping("/registro")
public class RegistroControlador {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public String mostrarFormularioRegistro(Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }
        // Crea un objeto Cliente vacío para que el formulario se complete con los datos proporcionados por el usuario
        return "registro"; // Devuelve la vista de registro
    }

    @PostMapping
    public String guardaCliente(Cliente cliente, Model model) {
        if (cliente.getPersona() == null) {
            model.addAttribute("error", "Debe ingresar los datos de la persona.");
            return "registro"; // Redirige a la página de registro
        }

        //null es igual si no encuentra el usuario y si lo encuentra pone el usuario 
        Cliente clienteExistente = clienteRepository.findByUsuario(cliente.getUsuario());

        if (clienteExistente != null) {
            model.addAttribute("errorRepetido", "El usuario ya está en uso.");
            return "registro"; // Redirige a la página de registro
        }

        // Asignar el rol por defecto
        Optional<Roles> rolPorDefecto = rolesRepository.findById(1L);
        // Busca el rol con ID 1 en la base de datos.

        if (rolPorDefecto.isPresent()) {
            cliente.getPersona().setRoles(rolPorDefecto.get());
            // Si el rol existe, se asigna a la persona del cliente.
            clienteRepository.save(cliente);
            // Guarda el cliente con la persona y el rol en la base de datos.
        } else {
            model.addAttribute("error", "No existe el rol.");
            // Si el rol no existe, muestra un mensaje de error en la vista.
        }

        return "redirect:/indice"; // Redirige a la página principal si el registro fue exitoso
    }
}
