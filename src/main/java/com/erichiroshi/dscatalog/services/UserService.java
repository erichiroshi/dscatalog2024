package com.erichiroshi.dscatalog.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erichiroshi.dscatalog.entities.Role;
import com.erichiroshi.dscatalog.entities.User;
import com.erichiroshi.dscatalog.entities.dtos.RoleDTO;
import com.erichiroshi.dscatalog.entities.dtos.UserDTO;
import com.erichiroshi.dscatalog.entities.dtos.UserInsertDTO;
import com.erichiroshi.dscatalog.mappers.RoleMapper;
import com.erichiroshi.dscatalog.mappers.UserMapper;
import com.erichiroshi.dscatalog.repositories.UserRepository;
import com.erichiroshi.dscatalog.services.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final RoleMapper roleMapper;

    public UserService(UserRepository userRepository, UserMapper mapper, RoleService roleService, PasswordEncoder passwordEncoder, RoleMapper roleMapper) {
        this.repository = userRepository;
        this.mapper = mapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.roleMapper = roleMapper;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        Page<User> users = repository.findAll(pageable);
        return users.map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = find(id);
        return mapper.toDto(user);
    }

    public UserDTO create(UserInsertDTO userInsertDto) {
        User user = mapper.toEntity(userInsertDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        for (RoleDTO roleDto : userInsertDto.roles()) {
            Role role = roleMapper.toEntity(roleService.findById(roleDto.id()));
            user.getRoles().add(role);
        }

        user = repository.save(user);
        return mapper.toDto(user);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        User user = find(id);
        user = mapper.partialUpdate(dto, user);
        user = repository.save(user);
        return mapper.toDto(user);
    }

    @Transactional
    public void deleteById(Long id) {
        find(id);
        repository.deleteById(id);
    }

    private User find(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found. Id: " + id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if (user == null) {
            log.error("User not found: {}", username);
            throw new UsernameNotFoundException("Email not found");
        }
        log.info("User found: {}", username);
        return user;
    }
}
