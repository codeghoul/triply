package me.jysh.triply.service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.jysh.triply.entity.RoleEntity;
import me.jysh.triply.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for interacting with {@link RoleEntity} repository.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RoleService {

  private final RoleRepository repository;

  public Map<String, RoleEntity> getRoleEntityMap(final Collection<String> roles) {
    return repository.findAllByNameIn(roles)
        .stream()
        .collect(Collectors.toMap(RoleEntity::getName, Function.identity()));
  }
}
