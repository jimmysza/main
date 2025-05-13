package eta.main.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eta.main.modeloEntidad.Cliente;
import eta.main.modeloEntidad.Persona;
import eta.main.modeloEntidad.Roles;
import eta.main.repositorio.ClienteRepository;
import eta.main.repositorio.RolesRepository;
import java.util.Optional;

@Controller
@RequestMapping("/actividad")
public class ActividadControlador {

    @Autowired
    private RolesRepository rolesRepository;
    // Inyecta automáticamente el repositorio que maneja la entidad Roles,
    // lo que permite acceder a los métodos para consultar y manipular roles en la base de datos.

    @Autowired
    private ClienteRepository clienteRepository;
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

        model.addAttribute("clientes", clienteRepository.findAll());
        // Agrega la lista de todos los clientes existentes al modelo, 
        // para mostrarlos en una tabla o lista en la misma vista si se desea.

        return "cliente";
        // Retorna el nombre de la vista (por ejemplo, cliente.html o cliente.jsp) que se debe renderizar.
    }

    @PostMapping // guardaCliente recibe dato de la vista y los guarda en cliente
    public String guardaCliente(@ModelAttribute("guardaCliente") Cliente cliente, Model model) {
        if (cliente.getPersona() == null) {
            model.addAttribute("error", "Debe ingresar los datos de la persona.");
            // Si no se ha ingresado la persona, muestra un mensaje de error en la vista.
            return "cliente";
        }

        Cliente clienteExistente = clienteRepository.findByUsuario(cliente.getUsuario());

        if (clienteExistente != null) {
            model.addAttribute("errorRepetido", "El usuario ya está en uso.");
            return "registro"; // Redirige a la página de registro con el mensaje de error
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

        return "redirect:/cliente";
        // Redirige a la vista de clientes (con la lista actualizada).
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Long idCliente) {
        if (clienteRepository.existsById(idCliente)) {
            clienteRepository.deleteById(idCliente);
            // Si el cliente existe, se elimina de la base de datos.
            return "redirect:/cliente";
            // Redirige a la vista de clientes (con la lista actualizada).
        } else {
            return "redirect:/cliente?error=deleteFailed";
            // Si el cliente no existe, redirige con un mensaje de error.
        }
    }

    @PostMapping("/editar")
    public String editarClienteParcial(@ModelAttribute("cliente") Cliente cliente, Model model) {
        if (cliente.getIdCliente() == null || !clienteRepository.existsById(cliente.getIdCliente())) {
            model.addAttribute("error", "El cliente con ID proporcionado no existe.");
            // Si no existe un cliente con el ID proporcionado, muestra un mensaje de error en la vista.
            return "redirect:/cliente";
        }

        Cliente clienteExistente = clienteRepository.findById(cliente.getIdCliente()).orElseThrow();
        // Recupera el cliente existente desde la base de datos utilizando el ID proporcionado.

        Roles rolPorDefecto = rolesRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        // Obtiene el rol por defecto (ID 1), lanzando una excepción si no se encuentra.

        // Actualiza solo si los campos vienen con valores
        if (cliente.getPersona() != null) {
            if (cliente.getPersona().getNombre() != null && !cliente.getPersona().getNombre().isBlank()) {
                clienteExistente.getPersona().setNombre(cliente.getPersona().getNombre());
                // Si el nombre no está vacío, se actualiza el nombre del cliente existente.
            }

            if (cliente.getPersona().getCorreoElectronico() != null && !cliente.getPersona().getCorreoElectronico().isBlank()) {
                clienteExistente.getPersona().setCorreoElectronico(cliente.getPersona().getCorreoElectronico());
                // Si el correo electrónico no está vacío, se actualiza el correo del cliente existente.
            }

            // Siempre se asegura que el rol esté presente
            clienteExistente.getPersona().setRoles(rolPorDefecto);
            // Asigna el rol por defecto a la persona del cliente.
        }

        clienteRepository.save(clienteExistente);
        // Guarda los cambios realizados al cliente existente en la base de datos.

        return "redirect:/cliente";
        // Redirige a la vista de clientes (con la lista actualizada).
    }
}
