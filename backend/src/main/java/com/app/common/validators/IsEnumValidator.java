package com.app.common.validators;

import com.app.common.annotations.IsEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IsEnumValidator implements ConstraintValidator<IsEnum, Object> {

  private Class<? extends Enum<?>> enumClass;

  @Override
  public void initialize(IsEnum constraintAnnotation) {
    this.enumClass = constraintAnnotation.value();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }

    try {
      // If incoming value is the correct enum instance
      if (enumClass.isInstance(value)) {
        return true;
      }

      // If incoming value is a String
      if (value instanceof String input) {
        for (Enum<?> constant : enumClass.getEnumConstants()) {
          if (constant.name().equals(input)) {
            return true;
          }
        }
      }

      return false;
    } catch (Exception e) {
      return false;
    }
  }
}
