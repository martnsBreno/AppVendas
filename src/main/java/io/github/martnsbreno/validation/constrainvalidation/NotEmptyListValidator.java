package io.github.martnsbreno.validation.constrainvalidation;

import io.github.martnsbreno.validation.NotEmptyListPedido;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyListPedido, List> {
    @Override
    public void initialize(NotEmptyListPedido constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        return list != null && !list.isEmpty();
    }


}
