package javagrinko.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * В проекте Spring Data MongoDB по умолчанию не поддерживается
 * проверка полей перед сохранением документов в коллекции Mongo.
 * Это недоразумение исправляется с помощью небольшого аспекта,
 * использующего javax.validation.Validator и стандарт JSR-349 Bean Validation
 * @author Artem Grinko
 * @email javagrinko@gmail.com
 */
@Aspect
@Component
public class ValidateMongo {
    /**
     * @Bean
       LocalValidatorFactoryBean validator(){
         return new LocalValidatorFactoryBean();
       }
     */
    @Autowired
    Validator validator;

    @Pointcut("execution(* org.springframework.data.mongodb.repository.MongoRepository.save(*))")
    public void mongoSave() {
    }

    @Before("mongoSave()")
    public void validateJSR349(JoinPoint joinPoint) throws Throwable {
        Object o = joinPoint.getArgs()[0];
        if (o instanceof Iterable) {
            Iterable entities = (Iterable) o;
            for (Object entity : entities) {
                checkEntity(entity);
            }
        } else {
            checkEntity(o);
        }
    }

    private void checkEntity(Object entity) {
        Set<ConstraintViolation<Object>> violations = validator.validate(entity);
        if (violations.size() > 0) {
            for (ConstraintViolation<Object> violation : violations) {
                throw new ValidationException(violation.getPropertyPath() + " " + violation.getMessage());
            }
        }
    }
}
