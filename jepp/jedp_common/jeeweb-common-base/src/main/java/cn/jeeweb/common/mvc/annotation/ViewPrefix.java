package cn.jeeweb.common.mvc.annotation;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewPrefix {
	String value() default "";
}