package eta.main.controlador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Persona;
import eta.main.modeloEntidad.Roles;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.RolesRepository;

@Controller
public class indiceControlador {
    

    @GetMapping("/indice")
    public String getIndice(){
        return "indice";
    }

    @GetMapping("/ingreso")
    public String getIngreso(){
        return "ingreso";
    }
}
