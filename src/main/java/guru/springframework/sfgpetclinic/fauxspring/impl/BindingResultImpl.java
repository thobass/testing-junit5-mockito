package guru.springframework.sfgpetclinic.fauxspring.impl;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;

public class BindingResultImpl implements BindingResult {

    private boolean errors;

    @Override
    public void rejectValue(String lastName, String notFound, String not_found) {
        this.errors = true;
    }

    @Override
    public boolean hasErrors() {
        return errors;
    }
}
