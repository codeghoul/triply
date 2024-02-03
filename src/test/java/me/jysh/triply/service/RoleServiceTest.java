package me.jysh.triply.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import me.jysh.triply.entity.RoleEntity;
import me.jysh.triply.mocks.TestMocks;
import me.jysh.triply.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = RoleService.class)
@ExtendWith(SpringExtension.class)
class RoleServiceTest {

  @MockBean
  private RoleRepository roleRepository;

  @Autowired
  private RoleService roleService;

  @Test
  void testGetRoleEntityMap() {

    Collection<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
    final List<RoleEntity> roleEntities = TestMocks.getRoleEntities();

    when(roleRepository.findAllByNameIn(roles)).thenReturn(roleEntities);

    Map<String, RoleEntity> result = roleService.getRoleEntityMap(roles);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertTrue(result.containsKey("ROLE_USER"));
    assertTrue(result.containsKey("ROLE_ADMIN"));
  }
}
