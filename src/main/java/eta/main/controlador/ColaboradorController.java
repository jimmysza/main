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
import eta.main.modeloEntidad.Colaborador;
import eta.main.modeloEntidad.Persona;
import eta.main.modeloEntidad.Roles;
import eta.main.repositorio.ColaboradorRepository;
import eta.main.repositorio.PersonaRepository;
import eta.main.repositorio.RolesRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/colaborador")
public class ColaboradorController {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private PersonaRepository personaRepository;

    private void cargarDatosColaborador(Model model) {
        model.addAttribute("ListaColaborador", colaboradorRepository.findByPersona_Roles_IdRol(2L));
        model.addAttribute("CantidadColaborador", colaboradorRepository.count());
    }

    @GetMapping
    public String mostrarColaboradores(Model model, HttpSession session) {
        Colaborador nuevoColaborador = new Colaborador();
        nuevoColaborador.setPersona(new Persona());

        model.addAttribute("colaboradorEntidad", nuevoColaborador);
        model.addAttribute("ListaColaborador", colaboradorRepository.findByPersona_Roles_IdRol(2L));
        model.addAttribute("CantidadColaborador", colaboradorRepository.count());
        // Verifica si hay un admin logueado
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }
        return "bd/colaborador";
    }

    @PostMapping
    public String guardarColaborador(@ModelAttribute("colaboradorEntidad") Colaborador colaborador, Model model) {
        if (colaborador.getPersona() == null) {
            model.addAttribute("error", "Debe ingresar los datos de la persona.");
            cargarDatosColaborador(model);
            return "bd/colaborador";
        }

        if (colaboradorRepository.findByUsuario(colaborador.getUsuario()) != null) {
            model.addAttribute("error", "El usuario ya está en uso.");
            cargarDatosColaborador(model);
            return "bd/colaborador";
        }

        if (personaRepository.findByCorreoElectronico(colaborador.getPersona().getCorreoElectronico()) != null) {
            model.addAttribute("error", "El correo electrónico ya está en uso.");
            cargarDatosColaborador(model);
            return "bd/colaborador";
        }

        if (colaboradorRepository.findByIdentificacion(colaborador.getIdentificacion()) != null) {
            model.addAttribute("error", "La identificación ya está en uso.");
            cargarDatosColaborador(model);
            return "bd/colaborador";
        }

        if (colaboradorRepository.findByRuc(colaborador.getRuc()) != null) {
            model.addAttribute("error", "El RUC ya está en uso.");
            cargarDatosColaborador(model);
            return "bd/colaborador";
        }

        Optional<Roles> rolPorDefecto = rolesRepository.findById(2L);
        if (rolPorDefecto.isPresent()) {
            colaborador.getPersona().setRoles(rolPorDefecto.get());
            colaboradorRepository.save(colaborador);
        } else {
            model.addAttribute("error", "No existe el rol.");
            cargarDatosColaborador(model);
            return "bd/colaborador";
        }

        return "redirect:/colaborador";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarColaborador(@PathVariable("id") Long idColaborador) {
        Optional<Colaborador> colaboradorOpt = colaboradorRepository.findById(idColaborador);
        if (colaboradorOpt.isPresent()) {
            Colaborador colaborador = colaboradorOpt.get();
            Persona persona = colaborador.getPersona();

            colaboradorRepository.delete(colaborador);
            personaRepository.delete(persona);

            return "redirect:/colaborador";
        } else {
            return "redirect:/colaborador?error=notfound";
        }
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Colaborador obtenerColaboradorParaEdicion(@PathVariable("id") Long id) {
        return colaboradorRepository.findById(id).orElse(null);
    }

    @GetMapping("/editar-form/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        Colaborador colaborador = colaboradorRepository.findById(id).orElse(null);
        model.addAttribute("colaboradorEntidad", colaborador);
        return "bd/edits/colaborador-editar";
    }

    @PostMapping("/editar")
    public String actualizarColaborador(HttpServletRequest request, Model model) {
        Long idColaborador = Long.valueOf(request.getParameter("idColaborador"));
        String nombre = request.getParameter("persona.nombre");
        String correo = request.getParameter("persona.correoElectronico");
        String usuario = request.getParameter("usuario");

        Optional<Colaborador> colaboradorExistente = colaboradorRepository.findById(idColaborador);
        if (colaboradorExistente.isPresent()) {
            Colaborador original = colaboradorExistente.get();
            original.setUsuario(usuario);
            // original.setContrasena(request.getParameter("contrasena")); // Si tienes contraseña

            if (original.getPersona() != null) {
                original.getPersona().setNombre(nombre);
                original.getPersona().setCorreoElectronico(correo);
            }

            Optional<Roles> rolPorDefecto = rolesRepository.findById(2L);
            rolPorDefecto.ifPresent(r -> original.getPersona().setRoles(r));
            colaboradorRepository.save(original);
        }
        return "redirect:/colaborador";
    }

}
