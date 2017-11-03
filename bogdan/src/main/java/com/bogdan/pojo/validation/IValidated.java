package com.bogdan.pojo.validation;

import com.bogdan.exceptions.DataNotValidException;
import org.apache.log4j.Logger;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public interface IValidated {
    static <T extends IValidated> void validate(T object, Validator validator, Logger logger) throws DataNotValidException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (constraintViolations.size() != 0) {
            logger.info(String.format("Кол-во ошибок: %d",
                    constraintViolations.size()));
            for (ConstraintViolation<Object> cv : constraintViolations)
                logger.info(String.format(
                        "Внимание, ошибка! property: [%s], value: [%s], message: [%s]",
                        cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage()));
            throw new DataNotValidException();
        }
    }
}

