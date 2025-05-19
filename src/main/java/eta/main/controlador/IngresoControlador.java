package eta.main.controlador;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eta.main.modeloEntidad.Admin;
import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Colaborador;
import eta.main.modeloEntidad.Persona;
import eta.main.repositorio.AdminRepository;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.ColaboradorRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ingreso")
public class IngresoControlador {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/cliente")
    public String getIngreso(Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
            model.addAttribute("persona", new Persona());
        }
        return "registroNLogins/ingreso";
    }

    @GetMapping("/colaborador")
    public String getIngresoColaborador(Model model) {
        if (!model.containsAttribute("colaboradorEntidad")) {
            model.addAttribute("colaboradorEntidad", new Colaborador());
            model.addAttribute("persona", new Persona());
        }
        return "registroNLogins/ColabIngreso";
    }

    @GetMapping("/admin")
    public String getIngresoAdmin(Model model) {
        if (!model.containsAttribute("adminEntidad")) {
            model.addAttribute("adminEntidad", new Admin());
            model.addAttribute("persona", new Persona());
        }
        return "registroNLogins/AdminIngreso";
    }


    @PostMapping("/cliente")
    public String IniciarSesion(Cliente cliente, Model model, HttpSession session) {
        Cliente clienteExistente = clienteRepository.findByUsuarioAndContrasena(
                cliente.getUsuario(),
                cliente.getContrasena()
        );

        if (clienteExistente != null) {
            session.setAttribute("usuarioLogueado", clienteExistente);
            session.setAttribute("ultimoIngreso", LocalDateTime.now());
            return "redirect:/indice";
        } else {
            System.out.println("ERROR LOGIN CLIENTE: Usuario: " + cliente.getUsuario() + " - Contraseña incorrecta o usuario no existe.");
            model.addAttribute("cliente", cliente);
            model.addAttribute("ContraseñaIncorrecta", "La Contraseña o Usuario es Incorrecto");
            return "registroNLogins/ingreso";
        }
    }

    @PostMapping("/colaborador")
    public String IniciarSesionColaborador(Colaborador colaborador, Model model, HttpSession session) {
        Colaborador colaboradorExistente = colaboradorRepository.findByUsuarioAndContrasena(
                colaborador.getUsuario(),
                colaborador.getContrasena()
        );

        if (colaboradorExistente != null) {
            session.setAttribute("usuarioLogueado", colaboradorExistente);
            session.setAttribute("ultimoIngresoColaborador", LocalDateTime.now());
            return "redirect:/actividadColaboradores";
        } else {
            System.out.println("ERROR LOGIN COLABORADOR: Usuario: " + colaborador.getUsuario() + " - Contraseña incorrecta o usuario no existe.");
            model.addAttribute("colaboradorEntidad", colaborador);
            model.addAttribute("ContraseñaIncorrecta", "La Contraseña o Usuario es Incorrecto");
            return "registroNLogins/ColabIngreso";
        }
    }

    @PostMapping("/admin")
    public String IniciarSesionAdmin(Admin admin, Model model, HttpSession session) {
        Admin adminExistente = adminRepository.findByUsuarioAndContrasena(
                admin.getUsuario(),
                admin.getContrasena()
        );

        if (adminExistente != null) {
            session.setAttribute("adminLogueado", adminExistente); // guardar el admin en la sesión
            return "redirect:/main";
        } else {
            System.out.println("ERROR LOGIN ADMIN: Usuario: " + admin.getUsuario() + " - Contraseña incorrecta o usuario no existe.");
            model.addAttribute("adminEntidad", admin);
            model.addAttribute("ContraseñaIncorrecta", "La Contraseña o Usuario es Incorrecto");
            return "registroNLogins/AdminIngreso";
        }
    }
// ...existing code...
}
