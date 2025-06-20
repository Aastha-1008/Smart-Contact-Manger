package validators;

import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile,MultipartFile>{

    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2;

    //type

    //height

    //width

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        
        if(file == null || file.isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File cannot be empty").addConstraintViolation();
            return false;
        }

        if(file.getSize() > MAX_FILE_SIZE){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less than 5MB").addConstraintViolation();
            return false;
        }

        return true;

    }

}
