package eta.main.controlador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eta.main.modeloEntidad.Actividad;
import eta.main.modeloEntidad.Colaborador;
import eta.main.repositorio.ActividadRepository;
import eta.main.repositorio.ColaboradorRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/actividadColaboradores")
public class ActividadColaboradorControlador {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    private void cargarDatosActividad(HttpSession session, Model model) {
        Colaborador colaborador = (Colaborador) session.getAttribute("usuarioLogueado");
        if (colaborador != null) {
            Long idColaborador = colaborador.getIdColaborador();
            model.addAttribute("ListaActividad", actividadRepository.findByColaborador_IdColaborador(idColaborador));
            model.addAttribute("CantidadActividad", actividadRepository.countByColaborador_IdColaborador(idColaborador));
        } else {
            model.addAttribute("ListaActividad", null);
            model.addAttribute("CantidadActividad", 0L);
        }
    }

    @GetMapping
    public String verActividadesColaborador(HttpSession session, Model model) {
        Colaborador colaborador = (Colaborador) session.getAttribute("ColaboradorLogueado"); // <--- CORREGIDO
        if (colaborador == null) {
            return "redirect:/ingreso/colaborador";
        }
        Long idColaborador = colaborador.getIdColaborador();
        model.addAttribute("ActividadEntidad", new Actividad());
        model.addAttribute("ListaActividad", actividadRepository.findByColaborador_IdColaborador(idColaborador));
        model.addAttribute("listaColaboradores", colaboradorRepository.findAll());
        model.addAttribute("CantidadActividad", actividadRepository.countByColaborador_IdColaborador(idColaborador));
        model.addAttribute("colaboradorLogueado", colaborador);

        return "bd/colaboradorActividad";
    }

    @PostMapping
    public String guardarActividad(HttpServletRequest request, Model model, HttpSession session) {
        // Obtener los datos del formulario
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        Integer cupoMaximo = Integer.valueOf(request.getParameter("cupoMaximo"));
        String ubicacion = request.getParameter("ubicacion");
        String categoria = request.getParameter("categoria");
        Long idColaborador = Long.valueOf(request.getParameter("idColaborador"));

        // Buscar el colaborador
        Optional<Colaborador> colaboradorOpt = colaboradorRepository.findById(idColaborador);
        if (colaboradorOpt.isPresent()) {
            Actividad actividad = new Actividad();
            actividad.setTitulo(titulo);
            actividad.setDescripcion(descripcion);
            actividad.setCupoMaximo(cupoMaximo);
            actividad.setUbicacion(ubicacion);
            actividad.setCategoria(categoria);
            actividad.setColaborador(colaboradorOpt.get());

            actividadRepository.save(actividad);
        } else {
            model.addAttribute("error", "No existe el colaborador con esa id.");
            cargarDatosActividad(session, model); // <-- aquí pasas ambos parámetros
            return "bd/actividadColaboradores";
        }

        return "redirect:/actividadColaboradores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarActividad(@PathVariable("id") Long idActividad) {
        Optional<Actividad> actividadOpt = actividadRepository.findById(idActividad);
        if (actividadOpt.isPresent()) {
            Actividad actividad = actividadOpt.get();
            actividadRepository.delete(actividad);
            return "redirect:/actividadColaboradores";
        } else {

            return "redirect:/actividadColaboradores?error=notfound";
        }
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Actividad obtenerActividadParaEdicion(@PathVariable("id") Long id) {
        return actividadRepository.findById(id).orElse(null);
    }

    @GetMapping("/editarColab-form/{id}")
    public String mostrarFormularioEdicionColab(@PathVariable("id") Long id, Model model) {
        Actividad actividad = actividadRepository.findById(id).orElse(null);
        model.addAttribute("ActividadEntidad", actividad);
        model.addAttribute("listaColaboradores", colaboradorRepository.findAll());
        return "bd/edits/actColab-editar";
    }

    @PostMapping("/editar")
    public String actualizarActividad(HttpServletRequest request, Model model, HttpSession session) {
        Long idActividad = Long.valueOf(request.getParameter("idActividad"));
        Long idColaborador = Long.valueOf(request.getParameter("idColaborador"));
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        Integer cupoMaximo = Integer.valueOf(request.getParameter("cupoMaximo"));
        String ubicacion = request.getParameter("ubicacion");
        String categoria = request.getParameter("categoria");
        Optional<Actividad> actividadExistente = actividadRepository.findById(idActividad);
        if (actividadExistente.isPresent()) {
            Actividad original = actividadExistente.get();
            original.setTitulo(titulo);
            original.setDescripcion(descripcion);
            original.setCupoMaximo(cupoMaximo);
            original.setUbicacion(ubicacion);
            original.setCategoria(categoria);

            // Actualizar colaborador si es necesario
            Optional<Colaborador> colaboradorOpt = colaboradorRepository.findById(idColaborador);
            if (colaboradorOpt.isPresent()) {
                original.setColaborador(colaboradorOpt.get());
            } else {
                model.addAttribute("error", "No existe el colaborador con esa id.");
                cargarDatosActividad(session, model);
                return "bd/actividadColaboradores";
            }

            actividadRepository.save(original);
        }
        return "redirect:/actividadColaboradores";
    }

}
