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
import eta.main.modeloEntidad.Admin;
import eta.main.modeloEntidad.Colaborador;
import eta.main.repositorio.ActividadRepository;
import eta.main.repositorio.ColaboradorRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/actividad")
public class ActividadControlador {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    private void cargarDatosActividad(Model model) {
        model.addAttribute("ListaActividad", actividadRepository);
        model.addAttribute("CantidadActividad", actividadRepository.count());

    }

    @GetMapping
    public String mostrarActividades(Model model, HttpSession session) {
        Actividad nuevaActividad = new Actividad();

        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        model.addAttribute("ActividadEntidad", nuevaActividad);
        model.addAttribute("ListaActividad", actividadRepository.findAll());
        model.addAttribute("listaColaboradores", colaboradorRepository.findAll());
        model.addAttribute("CantidadActividad", actividadRepository.count());
    
        
        model.addAttribute("adminLogueado", adminLogueado);

        return "bd/actividad";
    }

    @PostMapping
    public String guardarActividad(HttpServletRequest request, Model model) {
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
            cargarDatosActividad(model);
            return "bd/actividad";
        }

        return "redirect:/actividad";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarActividad(@PathVariable("id") Long idActividad) {
        Optional<Actividad> actividadOpt = actividadRepository.findById(idActividad);
        if (actividadOpt.isPresent()) {
            Actividad actividad = actividadOpt.get();
            actividadRepository.delete(actividad);
            return "redirect:/actividad";
        } else {

            return "redirect:/actividad?error=notfound";
        }
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Actividad obtenerActividadParaEdicion(@PathVariable("id") Long id) {
        return actividadRepository.findById(id).orElse(null);
    }

    @GetMapping("/editar-form/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        Actividad actividad = actividadRepository.findById(id).orElse(null);
        model.addAttribute("ActividadEntidad", actividad);
        model.addAttribute("listaColaboradores", colaboradorRepository.findAll());
        return "bd/edits/actividad-editar";
    }

    @PostMapping("/editar")
    public String actualizarActividad(HttpServletRequest request, Model model) {
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
                cargarDatosActividad(model);
                return "bd/actividad";
            }

            actividadRepository.save(original);
        }
        return "redirect:/actividad";
    }

    // Este método maneja las solicitudes GET para ver el detalle de una actividad específica según su ID.



}
