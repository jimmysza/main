package eta.main.controlador;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eta.main.modeloEntidad.Colaborador;
import eta.main.modeloEntidad.Fechas;
import eta.main.repositorio.ActividadRepository;
import eta.main.repositorio.FechasRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/fechasColaboradores")
public class FechasColaboradoresController {

    @Autowired
    private FechasRepository fechasRepository;

    @Autowired
    private ActividadRepository actividadRepository;

    @GetMapping
    public String mostrarFechasColaboradores(HttpSession session, Model model) {
        Colaborador colaboradorLogueado = (Colaborador) session.getAttribute
        ("ColaboradorLogueado");

        if (colaboradorLogueado == null) {
            return "redirect:/ingreso/colaborador";
        }

        Long idColaborador = colaboradorLogueado.getIdColaborador();
        model.addAttribute("fechasEntidad", new Fechas());
        model.addAttribute("listaFechas", fechasRepository.findByActividadColaboradorIdColaborador(idColaborador));
        model.addAttribute("listaActividades", actividadRepository.findByColaborador_IdColaborador(idColaborador));
        model.addAttribute("CantidadFechas", fechasRepository.countByActividadColaboradorIdColaborador(idColaborador));
        model.addAttribute("colaboradorLogueado", colaboradorLogueado);

        return "bd/colaboradorFecha";
    }

    @PostMapping
    public String guardarFechasColaboradores(@ModelAttribute("fechasEntidad") Fechas fechasEntidad, HttpSession session) {
        Colaborador colaboradorLogueado = (Colaborador) session.getAttribute("ColaboradorLogueado");
        if (colaboradorLogueado == null) {
            return "redirect:/ingreso/colaborador";
        }
        fechasRepository.save(fechasEntidad);
        return "redirect:/fechasColaboradores";
    }

    @GetMapping("/eliminar/{idFecha}")
    public String eliminarFechas(@PathVariable Long idFecha) //recibe el id de la tabla con th:each y lo elimina, en la vista 
    {
        //si hay una id igual elimina
        fechasRepository.deleteById(idFecha);
        return "redirect:/colaboradorFecha";
    }

    @GetMapping("/editar/{idFecha}")
    public String editarFechas(@PathVariable("idFecha") Long idFecha, Model model) //recibe el id de la tabla con th:each y lo edita, en la vista 
    {
        Fechas fechasEntidad = fechasRepository.findById(idFecha).orElse(null);
        model.addAttribute("fechasEntidad", fechasEntidad);
        return "bd/editarFechasColaboradores";
    }

    @GetMapping("/editar-form/{idFecha}")
    public String mostrarFormularioEdicion(@PathVariable("idFecha") Long idFecha, Model model, HttpSession session) {

        Colaborador colaboradorLogueado = (Colaborador) session.getAttribute("ColaboradorLogueado");
        if (colaboradorLogueado == null) {
            return "redirect:/ingreso/colaborador";
        }

        Fechas fechasEntidad = fechasRepository.findById(idFecha).orElse(null);
        model.addAttribute("fechasEntidad", fechasEntidad);
        model.addAttribute("listaActividades", actividadRepository.findAll());
        return "bd/edits/fechaColab-editar";
    }

    @PostMapping("/actualizar")
    public String actualizarFechas(HttpServletRequest request, Model model, HttpSession session) {

        Colaborador colaboradorLogueado = (Colaborador) session.getAttribute("ColaboradorLogueado");
        if (colaboradorLogueado == null) {
            return "redirect:/ingreso/colaborador";
        }

        Long idFecha = Long.valueOf(request.getParameter("idFecha"));
        Fechas fechasEntidad = fechasRepository.findById(idFecha).orElse(null);

        if (fechasEntidad != null) {
            fechasEntidad.setDia(LocalDate.parse(request.getParameter("dia")));
            fechasEntidad.setHora(LocalTime.parse(request.getParameter("hora")));
            fechasRepository.save(fechasEntidad);
        }

        return "redirect:/colaboradorFecha";
    }

}
