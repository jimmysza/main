package eta.main.controlador;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eta.main.modeloEntidad.Actividad;
import eta.main.modeloEntidad.Admin;
import eta.main.modeloEntidad.Fechas;
import eta.main.repositorio.ActividadRepository;
import eta.main.repositorio.FechasRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/fechas")
public class FechasController {

    @Autowired
    private FechasRepository fechasRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @GetMapping
    public String mostrarFechas(HttpSession session, Model model) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        model.addAttribute("fechasEntidad", new Fechas());
        model.addAttribute("listaFechas", fechasRepository.findAll());
        model.addAttribute("listaActividades", actividadRepository.findAll());
        model.addAttribute("CantidadFechas", fechasRepository.count());
        model.addAttribute("adminLogueado", adminLogueado);
        return "bd/fechas";
    }

    @PostMapping
    public String guardarFechas(HttpServletRequest request, Model model, HttpSession session) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        // VALIDACIÓN: Verifica que el parámetro no sea null ni vacío
        String idActividadStr = request.getParameter("id_actividad");
        if (idActividadStr == null || idActividadStr.isEmpty()) {
            // Puedes mostrar un mensaje de error o redirigir con un parámetro de error
            return "redirect:/fechas?error=actividad";
        }

        Long idActividad = Long.parseLong(idActividadStr);
        Actividad actividad = actividadRepository.findById(idActividad).orElse(null);

        Fechas fechasEntidad = new Fechas();
        fechasEntidad.setActividad(actividad);
        fechasEntidad.setDia(LocalDate.parse(request.getParameter("dia")));
        fechasEntidad.setHora(LocalTime.parse(request.getParameter("hora")));

        fechasRepository.save(fechasEntidad);

        return "redirect:/fechas";
    }

    @GetMapping("/eliminar/{idFecha}")
    public String eliminarFechas(@PathVariable Long idFecha, HttpSession session) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        fechasRepository.deleteById(idFecha);
        return "redirect:/fechas";
    }

    @GetMapping("/editar/{idFecha}")
    public String editarFechas(@PathVariable("idFecha") Long idFecha) {
        Fechas fechasEntidad = fechasRepository.findById(idFecha).orElse(null);
        return "bd/editarFechas";
    }

    @PostMapping("/actualizar")
    public String actualizarFechas(HttpServletRequest request, Model model, HttpSession session) {
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        Long idFecha = Long.valueOf(request.getParameter("idFecha"));
        Fechas fechasEntidad = fechasRepository.findById(idFecha).orElse(null);

        if (fechasEntidad != null) {
            fechasEntidad.setDia(LocalDate.parse(request.getParameter("dia")));
            fechasEntidad.setHora(LocalTime.parse(request.getParameter("hora")));
            fechasRepository.save(fechasEntidad);
        }

        return "redirect:/fechas";
    }

    @GetMapping("/editar-form/{idFecha}")
    public String mostrarFormularioEdicion(@PathVariable("idFecha") Long idFecha, Model model, HttpSession session) {
        
        Admin adminLogueado = (Admin) session.getAttribute("adminLogueado");
        if (adminLogueado == null) {
            return "redirect:/ingreso/admin";
        }

        Fechas fechasEntidad = fechasRepository.findById(idFecha).orElse(null);
        model.addAttribute("fechasEntidad", fechasEntidad);
        model.addAttribute("listaActividades", actividadRepository.findAll());
        model.addAttribute("adminLogueado", adminLogueado);
        return "bd/edits/fechas-editar";
    }

}
