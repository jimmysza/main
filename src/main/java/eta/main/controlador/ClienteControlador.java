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

import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Persona;
import eta.main.modeloEntidad.Roles;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.PersonaRepository;
import eta.main.repositorio.RolesRepository;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private RolesRepository rolesRepository;
    // Inyecta automáticamente el repositorio que maneja la entidad Roles,
    // lo que permite acceder a los métodos para consultar y manipular roles en la base de datos.

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;
    // Inyecta automáticamente el repositorio de Cliente para realizar operaciones CRUD con clientes.

    @GetMapping
    public String MostrarClientes(Model model) {
        // Generalmente se usa para mostrar la página del formulario de clientes.

        Cliente nuevoCliente = new Cliente();
        // Crea una nueva instancia de Cliente (vacía) para que se use en el formulario.

        nuevoCliente.setPersona(new Persona());
        // También crea una instancia vacía de Persona dentro del Cliente,
        // para evitar errores de null y permitir que el formulario de la vista acceda a los campos de Persona.

        model.addAttribute("cliente", nuevoCliente);
        // Agrega el nuevo cliente al modelo con el nombre "cliente",
        // para que pueda ser utilizado en el formulario de creación en la vista (ej. con th:object).

        model.addAttribute("clientes", clienteRepository.findByPersona_Roles_IdRol(1L));
        // Agrega la lista de todos los clientes existentes al modelo,
        // para mostrarlos en una tabla o lista en la misma vista si se desea.

        model.addAttribute("CantidaCliente", clienteRepository.count());

        return "bd/cliente";
        // Retorna el nombre de la vista (por ejemplo, cliente.html o cliente.jsp) que se debe renderizar.
    }

    @PostMapping // guardaCliente recibe dato de la vista y los guarda en cliente
    public String guardaCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {
        if (cliente.getPersona() == null) {
            model.addAttribute("error", "Debe ingresar los datos de la persona.");
            // Si no se ha ingresado la persona, muestra un mensaje de error en la vista.
            return "redirect:/bd/cliente";
        }

        if (clienteRepository.findByUsuario(cliente.getUsuario()) != null) {
            return "redirect:/bd/cliente";
        }

        // Verifica si el correo electrónico ya existe
        if (personaRepository.findByCorreoElectronico(cliente.getPersona().getCorreoElectronico()) != null) {
            return "redirect:/bd/cliente";
        }

        Optional<Roles> rolPorDefecto = rolesRepository.findById(1L);
        // Busca el rol con ID 1 en la base de datos.

        if (rolPorDefecto.isPresent()) {
            cliente.getPersona().setRoles(rolPorDefecto.get());
            // Si el rol existe, se asigna a la persona del cliente.
            clienteRepository.save(cliente);
            // Guarda el cliente con la persona y el rol en la base de datos.
        } else {
            model.addAttribute("error", "No existe el rol.");
            // Si el rol no existe, muestra un mensaje de error en la vista.
        }

        return "redirect:/bd/cliente";
        // Redirige a la vista de clientes (con la lista actualizada).
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Long idCliente) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            Persona persona = cliente.getPersona();

            clienteRepository.delete(cliente); // elimina el cliente
            personaRepository.delete(persona); // elimina la persona (si no tiene restricciones)

            return "redirect:/bd/cliente";
        } else {
            return "redirect:/bd/cliente?error=notfound";
        }
    }

    @GetMapping("/editar/{id}")
    @ResponseBody
    public Cliente obtenerClienteParaEdicion(@PathVariable("id") Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @GetMapping("/editar-form/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        model.addAttribute("cliente", cliente);
        return "bd/edits/cliente-editar"; // Nombre de la nueva vista para editar
    }

    @PostMapping("/editar")
    public String actualizarCliente(HttpServletRequest request, Model model) {
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
        return "redirect:/bd/cliente";
    }

}
