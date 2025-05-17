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
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping
    public String mostrarClientes(Model model, HttpSession session) {
        // Verifica si hay un admin logueado
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }
            
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setPersona(new Persona());

        model.addAttribute("cliente", nuevoCliente);
        model.addAttribute("clientes", clienteRepository.findByPersona_Roles_IdRol(1L));
        model.addAttribute("adminLogueado", adminLogueado);
        model.addAttribute("CantidadCliente", clienteRepository.count());

        return "bd/cliente";
    }

    @PostMapping
    public String guardaCliente(@ModelAttribute("cliente") Cliente cliente, Model model, HttpSession session) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        if (cliente.getPersona() == null) {
            model.addAttribute("error", "Debe ingresar los datos de la persona.");
            return "redirect:/cliente";
        }

        if (clienteRepository.findByUsuario(cliente.getUsuario()) != null) {
            model.addAttribute("error", "El usuario ya está en uso.");
            return "redirect:/cliente";
        }

        if (personaRepository.findByCorreoElectronico(cliente.getPersona().getCorreoElectronico()) != null) {
            model.addAttribute("error", "El correo electrónico ya está en uso.");
            return "redirect:/cliente";
        }

        Optional<Roles> rolPorDefecto = rolesRepository.findById(1L);
        if (rolPorDefecto.isPresent()) {
            cliente.getPersona().setRoles(rolPorDefecto.get());
            clienteRepository.save(cliente);
        } else {
            model.addAttribute("error", "No existe el rol.");
        }

        return "redirect:/cliente";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Long idCliente, HttpSession session) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            Persona persona = cliente.getPersona();

            clienteRepository.delete(cliente);
            if (persona != null) {
                personaRepository.delete(persona);
            }
        }
        return "redirect:/cliente";
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Cliente obtenerClienteParaEdicion(@PathVariable("id") Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @GetMapping("/editar-form/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model, HttpSession session) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        Cliente cliente = clienteRepository.findById(id).orElse(null);
        model.addAttribute("cliente", cliente);
        return "bd/edits/cliente-editar";
    }

    @PostMapping("/editar")
    public String actualizarCliente(HttpServletRequest request, Model model, HttpSession session) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        Long idCliente = Long.valueOf(request.getParameter("idCliente"));
        String nombre = request.getParameter("persona.nombre");
        String correo = request.getParameter("persona.correoElectronico");
        String usuario = request.getParameter("usuario");

        Optional<Cliente> clienteExistente = clienteRepository.findById(idCliente);
        if (clienteExistente.isPresent()) {
            Cliente original = clienteExistente.get();
            original.setUsuario(usuario);
            // Si tienes campo de contraseña, agrégalo aquí
            // original.setContrasena(request.getParameter("contrasena"));

            if (original.getPersona() != null) {
                original.getPersona().setNombre(nombre);
                original.getPersona().setCorreoElectronico(correo);
            }

            Optional<Roles> rolPorDefecto = rolesRepository.findById(1L);
            rolPorDefecto.ifPresent(r -> original.getPersona().setRoles(r));
            clienteRepository.save(original);
        }
        return "redirect:/cliente";
    }
}