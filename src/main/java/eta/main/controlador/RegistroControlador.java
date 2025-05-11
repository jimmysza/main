package eta.main.controlador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Persona;
import eta.main.modeloEntidad.Roles;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.PersonaRepository;
import eta.main.repositorio.RolesRepository;

@Controller
@RequestMapping("/registro")
public class RegistroControlador {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping
    public String mostrarFormularioRegistro(Model model) {
        if (!model.containsAttribute("clienteEntidad")) {
            Cliente cliente = new Cliente();
            cliente.setPersona(new Persona()); // Asocia una persona vacía
            model.addAttribute("clienteEntidad", cliente);
        }
        return "registro"; // Vista del formulario
    }

    @PostMapping
    public String guardarCliente(Cliente cliente, RedirectAttributes redirectAttributes) {
        if (cliente.getPersona() == null) {
            redirectAttributes.addFlashAttribute("error", "Debe ingresar los datos de la persona.");
            redirectAttributes.addFlashAttribute("clienteEntidad", cliente);
            return "redirect:/registro";
        }

        // Verifica si el usuario ya existe
        if (clienteRepository.findByUsuario(cliente.getUsuario()) != null) {
            redirectAttributes.addFlashAttribute("errorRepetido", "El usuario ya está en uso.");
            redirectAttributes.addFlashAttribute("clienteEntidad", cliente);
            return "redirect:/registro";
        }

        // Verifica si el correo electrónico ya existe
        if (personaRepository.findByCorreoElectronico(cliente.getPersona().getCorreoElectronico()) != null) {
            redirectAttributes.addFlashAttribute("errorRepetidoEmail", "El correo electrónico ya está en uso.");
            redirectAttributes.addFlashAttribute("clienteEntidad", cliente);
            return "redirect:/registro";
        }

        // Asigna el rol por defecto
        Optional<Roles> rolPorDefecto = rolesRepository.findById(1L);
        if (rolPorDefecto.isPresent()) {
            cliente.getPersona().setRoles(rolPorDefecto.get());
        } else {
            redirectAttributes.addFlashAttribute("error", "No existe el rol por defecto.");
            redirectAttributes.addFlashAttribute("clienteEntidad", cliente);
            return "redirect:/registro";
        }

        try {
            clienteRepository.save(cliente); // Guarda todo junto
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar los datos. Intente nuevamente.");
            redirectAttributes.addFlashAttribute("clienteEntidad", cliente);
            return "redirect:/registro";
        }

        return "redirect:/indice";
    }
}
