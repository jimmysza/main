package eta.main.config;

import org.springframework.boot.CommandLineRunner;//permite ejecutar codigo al iniciar la aplicacion
import org.springframework.context.annotation.Bean;//permite definir beans
import org.springframework.context.annotation.Configuration;//permite definir configuraciones

import eta.main.modeloEntidad.Roles;
import eta.main.repositorio.RolesRepository;// llama su repository

// esta clase genera valores iniciales en la tabla roles

@Configuration
public class DatosIniciales {
    
    @Bean
    CommandLineRunner cargarRoles(RolesRepository rolesRepository) {
        return args -> {
            if (rolesRepository.count() == 0) {
                rolesRepository.save(new Roles("Cliente"));
                rolesRepository.save(new Roles("Colaborador"));
                rolesRepository.save(new Roles("Admin"));
                System.out.println("Roles iniciales creados");
            }
        };
    }
}
