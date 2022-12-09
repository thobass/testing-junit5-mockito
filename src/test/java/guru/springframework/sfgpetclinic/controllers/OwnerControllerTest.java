package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.fauxspring.impl.BindingResultImpl;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";

    @InjectMocks
    private OwnerController controller;

    @Mock
    private OwnerService ownerService;

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willAnswer(invocation -> {
            String name = invocation.getArgument(0);
            List<Owner> ownerList = new ArrayList<>();

            if(name.equals("%Buck%")){
                ownerList.add( new Owner(1l, "Jim","Buck"));
                return ownerList;
            }else if (name.equals("%DontFindMe%")){
                return ownerList;
            }else if (name.equals("%FindMe%")){
                ownerList.add( new Owner(1l, "Jim","Buck"));
                ownerList.add( new Owner(2l, "Tom","Basset"));
                return ownerList;
            }

            throw new RuntimeException("Invalid Argument");
        });

    }

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

    @Test
    void processFindFormWildcardStringAnnotation() {
        //GIVEN
        Owner owner = new Owner(1l, "Jim","Buck");

        //WHEN
        String viewName = controller.processFindForm(owner, bindingResult, null);

        //THEN
        assertThat("%Buck%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("redirect:/owners/1").isEqualToIgnoringCase(viewName);
    }

    @Test
    void processFindFormWildcardNotFound() {
        //GIVEN
        Owner owner = new Owner(1l, "Jim","DontFindMe");

        //WHEN
        String viewName = controller.processFindForm(owner, bindingResult, null);

        //THEN
        assertThat("%DontFindMe%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/findOwners").isEqualToIgnoringCase(viewName);
    }

    @Test
    void processFindFormWildcardFound() {
        //GIVEN
        Owner owner = new Owner(1l, "Jim","FindMe");
        InOrder inOrder = inOrder(ownerService, model);

        //WHEN
        String viewName = controller.processFindForm(owner, bindingResult, model);

        //THEN
        assertThat("%FindMe%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
        assertThat("owners/ownersList").isEqualToIgnoringCase(viewName);

        //inOrder asserts
        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        inOrder.verify(model).addAttribute(anyString(), anyList());
    }

    @Test
    void processFindFormWildcardString() {
        //GIVEN
        Owner owner = new Owner(1l, "Jim","Buck");
        List<Owner> ownerList = new ArrayList<>();
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);

        //WHEN
        String viewName = controller.processFindForm(owner, bindingResult, null);

        //THEN
        assertThat("%Buck%").isEqualToIgnoringCase(captor.getValue());
    }
}
