package espe.edu.ec.authservice.controller;

import espe.edu.ec.authservice.model.Role;
import espe.edu.ec.authservice.repository.RoleRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleRepository roleRepo;

    public RoleController(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @GetMapping
    public List<Role> list() {
        return roleRepo.findAll();
    }

    @PostMapping
    public Role create(@Valid @RequestBody Role role) {
        return roleRepo.save(role);
    }
}
