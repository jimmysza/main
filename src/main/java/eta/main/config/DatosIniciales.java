package eta.main.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eta.main.modeloEntidad.Roles;
import eta.main.repositorio.RolesRepository;



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
