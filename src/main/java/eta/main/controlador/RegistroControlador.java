package eta.main.controlador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eta.main.modeloEntidad.Admin;
import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Colaborador;
import eta.main.modeloEntidad.Persona;
import eta.main.modeloEntidad.Roles;
import eta.main.repositorio.AdminRepository;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.ColaboradorRepository;
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

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/cliente")
    public String mostrarFormularioRegistroCliente(Model model) {
        if (!model.containsAttribute("clienteEntidad")) {
            Cliente cliente = new Cliente();
            cliente.setPersona(new Persona());
            model.addAttribute("clienteEntidad", cliente);
        }
        return "registroNLogins/registro";
    }

    @GetMapping("/colaborador")
    public String mostrarFormularioRegistroColaborador(Model model) {
        if (!model.containsAttribute("colaboradorEntidad")) {
            Colaborador colaborador = new Colaborador();
            colaborador.setPersona(new Persona());
            model.addAttribute("colaboradorEntidad", colaborador);
        }
        return "registroNLogins/ColabRegistro";
    }

    @GetMapping("/admin")
    public String mostrarFormularioRegistroAdmin(Model model) {
        if (!model.containsAttribute("adminEntidad")) {
            Admin admin = new Admin();
            admin.setPersona(new Persona());
            model.addAttribute("adminEntidad", admin);
        }
        return "registroNLogins/AdminRegistro";
    }

    @PostMapping("/cliente")
    public String guardarRegistroCliente(Cliente cliente, RedirectAttributes redirectAttributes) {
        if (cliente.getPersona() == null) {
            redirectAttributes.addFlashAttribute("error", "Debe ingresar los datos de la persona.");
            redirectAttributes.addFlashAttribute("clienteEntidad", cliente);
            System.out.println("Error: " + cliente.getPersona());
            return "redirect:/registro/cliente";
        }

        if (clienteRepository.findByUsuario(cliente.getUsuario()) != null) {
            redirectAttributes.addFlashAttribute("errorRepetido", "El usuario ya está en uso.");
            redirectAttributes.addFlashAttribute("clienteEntidad", cliente);
            System.out.println("Error: " + cliente.getUsuario());
            return "redirect:/registro/cliente";
        }

        if (personaRepository.findByCorreoElectronico(cliente.getPersona().getCorreoElectronico()) != null) {
            redirectAttributes.addFlashAttribute("errorRepetidoEmail", "El correo electrónico ya está en uso.");
            System.out.println("Error: " + cliente.getPersona().getCorreoElectronico());
            redirectAttributes.addFlashAttribute("clienteEntidad", cliente);
            return "redirect:/registro/cliente";
        }

        Optional<Roles> rolPorDefecto = rolesRepository.findById(1L);
        if (rolPorDefecto.isPresent()) {
            cliente.getPersona().setRoles(rolPorDefecto.get());
        } else {
            System.out.println("Error: No existe el rol por defecto.");
            redirectAttributes.addFlashAttribute("error", "No existe el rol por defecto.");
            redirectAttributes.addFlashAttribute("clienteEntidad", cliente);
            return "redirect:/registro/cliente";
        }

        try {
            clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error al guardar los datos. Intente nuevamente.");
            redirectAttributes.addFlashAttribute("clienteEntidad", cliente);
            return "redirect:/registro/cliente";
        }

        return "redirect:/ingreso/cliente";
    }

    @PostMapping("/colaborador")
    public String guardarRegistroColaborador(@ModelAttribute("colaboradorEntidad") Colaborador colaborador, Model model) {
        if (colaborador.getPersona() == null) {
            model.addAttribute("error", "Debe ingresar los datos de la persona.");
            
            return "registroNLogins/ColabRegistro";
        }

        if (colaboradorRepository.findByUsuario(colaborador.getUsuario()) != null) {
            model.addAttribute("error", "El usuario ya está en uso.");
            
            return "registroNLogins/ColabRegistro";
        }

        if (personaRepository.findByCorreoElectronico(colaborador.getPersona().getCorreoElectronico()) != null) {
            model.addAttribute("error", "El correo electrónico ya está en uso.");
            
            return "registroNLogins/ColabRegistro";
        }

        if (colaboradorRepository.findByIdentificacion(colaborador.getIdentificacion()) != null) {
            model.addAttribute("error", "La identificación ya está en uso.");
            
            return "registroNLogins/ColabRegistro";
        }

        if (colaboradorRepository.findByRuc(colaborador.getRuc()) != null) {
            model.addAttribute("error", "El RUC ya está en uso.");
            
            return "registroNLogins/ColabRegistro";
        }

        Optional<Roles> rolPorDefecto = rolesRepository.findById(2L);
        if (rolPorDefecto.isPresent()) {
            colaborador.getPersona().setRoles(rolPorDefecto.get());
            colaboradorRepository.save(colaborador);
        } else {
            model.addAttribute("error", "No existe el rol.");
            
            return "registroNLogins/ColabRegistro";
        }

        return "redirect:/ingreso/colaborador";
    }

    @PostMapping("/admin")
    public String guardarRegistroAdmin(@ModelAttribute("adminEntidad") Admin admin, Model model) {
        if (admin.getPersona() == null) {
            model.addAttribute("error", "Debe ingresar los datos de la persona.");
            model.addAttribute("adminEntidad", admin);
            return "registroNLogins/AdminRegistro";
        }

        if (adminRepository.findByUsuario(admin.getUsuario()) != null) {
            model.addAttribute("error", "El usuario ya está en uso.");
            model.addAttribute("adminEntidad", admin);
            return "registroNLogins/AdminRegistro";
        }

        if (personaRepository.findByCorreoElectronico(admin.getPersona().getCorreoElectronico()) != null) {
            model.addAttribute("error", "El correo electrónico ya está en uso.");
            model.addAttribute("adminEntidad", admin);
            return "registroNLogins/AdminRegistro";
        }

        Optional<Roles> rolPorDefecto = rolesRepository.findById(3L); // 3L = admin
        if (rolPorDefecto.isPresent()) {
            admin.getPersona().setRoles(rolPorDefecto.get());
            adminRepository.save(admin);
        } else {
            model.addAttribute("error", "No existe el rol.");
            model.addAttribute("adminEntidad", admin);
            return "registroNLogins/AdminRegistro";
        }

        return "redirect:/ingreso/admin";
    }
}
