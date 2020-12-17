package com.bruno.caetano.dev.itemstorage.utils.annotation;


import com.bruno.caetano.dev.itemstorage.utils.validator.ValueOfEnumValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValueOfEnumValidator.class)
public @interface ValueOfEnum {

	Class<? extends Enum<?>> enumClass();

	String message() default "must be any of enum {enumClass}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
