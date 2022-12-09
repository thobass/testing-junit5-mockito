package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;

    @Spy
    PetMapService petService;

    @InjectMocks
    VisitController visitController;

    @Test
    void loadPetWithVisit() {
        //GIVEN
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(1L);
        Pet pet3 = new Pet(3L);

        petService.save(pet);
        petService.save(pet3);

        given(petService.findById(anyLong())).willCallRealMethod();

        //WHEN
        Visit visit = visitController.loadPetWithVisit(1L, model);

        //THEN
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(1L);
        verify(petService).findById(anyLong());
    }

    @Test
    void loadPetWithVisitWithStubbing() {
        //GIVEN
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(1L);
        Pet pet3 = new Pet(3L);

        petService.save(pet);
        petService.save(pet3);

        given(petService.findById(anyLong())).willReturn(pet3);

        //WHEN
        Visit visit = visitController.loadPetWithVisit(1L, model);

        //THEN
        assertThat(visit).isNotNull();
        assertThat(visit.getPet()).isNotNull();
        assertThat(visit.getPet().getId()).isEqualTo(3L);
        verify(petService).findById(anyLong());
    }
}
