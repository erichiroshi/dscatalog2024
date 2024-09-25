package com.erichiroshi.dscatalog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erichiroshi.dscatalog.entities.Role;
import com.erichiroshi.dscatalog.entities.dtos.RoleDTO;
import com.erichiroshi.dscatalog.mappers.RoleMapper;
import com.erichiroshi.dscatalog.repositories.RoleRepository;
import com.erichiroshi.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class RoleService {

    private final RoleRepository repository;
    private final RoleMapper mapper;

    public RoleService(RoleRepository roleRepository, RoleMapper mapper) {
        this.repository = roleRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public Page<RoleDTO> findAllPaged(Pageable pageable) {
        Page<Role> roles = repository.findAll(pageable);
        return roles.map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public RoleDTO findById(Long id) {
        Role role = find(id);
        return mapper.toDto(role);
    }

    @Transactional
    public RoleDTO create(RoleDTO roleDto) {
        Role role = mapper.toEntity(roleDto);
        role = repository.save(role);
        return mapper.toDto(role);
    }

    @Transactional
    public RoleDTO update(Long id, RoleDTO dto) {
        Role role = find(id);
        role = mapper.partialUpdate(dto, role);
        role = repository.save(role);
        return mapper.toDto(role);
    }

    @Transactional
    public void deleteById(Long id) {
        find(id);
        repository.deleteById(id);
    }

    private Role find(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role not found. Id: " + id));
    }
}
