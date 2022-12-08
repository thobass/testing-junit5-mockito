package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.impl.BindingResultImpl;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @InjectMocks
    private OwnerController controller;

    @Mock
    private OwnerService ownerService;

    @Mock
    BindingResult bindingResult;

    @Test
    void processCreationForm_bindingResultsHasErrors() {
        //GIVEN
        Owner owner = new Owner(1l, "Jim","Bob");
        given(bindingResult.hasErrors()).willReturn(true);

        //WHEN
        String viewName = controller.processCreationForm(owner, bindingResult);

        //THEN
        assertThat(viewName).isEqualToIgnoringCase(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }

    @Test
    void processCreationForm_bindingResultsHasNoErrors() {
        //GIVEN

        Owner owner = new Owner(5l, "Jim","Bob");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any(Owner.class))).willReturn(owner);

        //WHEN
        String viewName = controller.processCreationForm(owner, bindingResult);

        //THEN
        assertThat(viewName).isEqualToIgnoringCase( REDIRECT_OWNERS_5);
    }
}
