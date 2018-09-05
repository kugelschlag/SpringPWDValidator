package com.kugelschlag.SpringPwdValidator.validators;


import org.passay.PasswordData;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.RuleResultDetail;


/**
 * Just a silly example of a custom rule that could be written for password validation
 * Determines if the the sequence "fubar" is allowed in a password string
 * Leverages the org.passay password validation library
 */


public class FubarRule implements Rule {

    private static String FUBAR = "fubar";

    //Injected whether fubar is allowed
    private boolean fubarAllowed;


    public FubarRule(boolean fubarAllowed) {
        this.fubarAllowed = fubarAllowed;

    }


    @Override
    public RuleResult validate(final PasswordData passwordData) {
        final RuleResult result = new RuleResult(true);

        String pw1 = passwordData.getPassword();

        if (pw1.contains(FUBAR) && fubarAllowed) {
            result.setValid(true);


        } else if (pw1.contains(FUBAR) && !fubarAllowed) {
            result.setValid(false);
            result.getDetails().add(new RuleResultDetail("Failed Fubar Check, No Fubars Allowed!", null));
        }

        return result;
    }

    public boolean isFubarAllowed() {
        return fubarAllowed;
    }

    public void setFubarAllowed(boolean fubarAllowed) {
        this.fubarAllowed = fubarAllowed;
    }


}
