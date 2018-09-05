package com.kugelschlag.SpringPwdValidator.validators;


import org.passay.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;


/**
 * Custom Validation implementation of ConstraintValidator
 * Use "@CharterPWValid" on data object fields to apply the validation handling implemented here
 * CharterPWValidator leverages the org.passay password validation library for standard PW validation rules/checks
 * But also extends with custom rules (i.e. FubarRule) as an example to demonstrate flexibility and extendability
 */
@Component
public class CharterPWValidator implements ConstraintValidator<CharterPWValid, String> {

    @Value("${validation.fubar.allowed:true}")
    private boolean fubarAllowed;

    @Value("${validation.length.min:1}")
    private int minLength;

    @Value("${validation.length.max:12}")
    private int maxLength;

    //Max of 3 char sequence can be repeated before fail
    @Value("${validation.repeat.max:3}")
    private int repeatMax;

    //Minimum of 1 numerical digit
    @Value("${validation.digit.min:1}")
    private int minDigits;

    //Atleast one lower-case character
    @Value("${validation.lowercase.min:1}")
    private int minLowerCase;




    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        //First, leverage the Passay validation library, if the standard library rules fail then exit,
        //If not, then continue to any custom conditions that may be needed

        PasswordValidator validator = new PasswordValidator(Arrays.asList(

                new LengthRule(minLength, maxLength),

                //regex pattern for only lowercase and digits in a string
                new AllowedRegexRule("^[a-z0-9]+$"),

                new RepeatCharacterRegexRule(repeatMax),

                new CharacterRule(EnglishCharacterData.Digit, minDigits),

                new CharacterRule(EnglishCharacterData.LowerCase, minLowerCase),


                //silly custom rule to demo new requirements later addition
                new FubarRule(fubarAllowed)


        ));


        //Do the validation checking and create a list of messages if there are failures
        RuleResult result = validator.validate(new PasswordData(password));

        if (!result.isValid()) {
            for (String msg : validator.getMessages(result)) {

                //Build out the ConstraintValidatorContext so that the return response has the error message(s)
                //of which validations failed
                //Using the context and adding the constraint violations (if any) is the way to get the messages back to
                //the validation exception handler

                context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();

            }
            //Validation failed
            return false;
        }
        //Validation succeeded
        return true;
    }

}