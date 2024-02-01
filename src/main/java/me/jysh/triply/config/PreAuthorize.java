package me.jysh.triply.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code PreAuthorize} annotation is used to indicate that authorization checks should be
 * performed before the execution of the annotated method. It allows specifying an array of role
 * names required for access. This annotation can be applied at the method level.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuthorize {

  /**
   * Specifies an array of role names required for access to the annotated method.
   *
   * @return an array of role names required for access
   */
  String[] withRoles() default {};
}
