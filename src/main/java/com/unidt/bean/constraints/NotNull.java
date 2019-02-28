package com.unidt.bean.constraints;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface NotNull {
    String value() default "not null";
}
