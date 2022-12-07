package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void findByIdBddTest(){
        //Given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));

        //When
        Speciality foundSpecialty = service.findById(1L);

        //Then
        assertThat(foundSpecialty).isNotNull();
        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void findById(){
        //Given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));

        //When
        Speciality foundSpecialty = service.findById(1L);

        //Then
        assertThat(foundSpecialty).isNotNull();
        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteByObject() {
        //Given
        Speciality speciality = new Speciality();

        //When
        service.delete(speciality);

        //Then
        then(specialtyRepository).should().delete(any(Speciality.class));
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteById() {
        //Given

        //When
        service.deleteById(1l);
        service.deleteById(1l);

        //Then
        then(specialtyRepository).should(times(2)).deleteById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteByIdAtLeastOnce() {
        //Given

        //When
        service.deleteById(1l);
        service.deleteById(1l);

        //Then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1l);
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteByIdAtMost() {
        //Given

        //When
        service.deleteById(1l);
        service.deleteById(1l);

        //Then
        then(specialtyRepository).should(atMost(5)).deleteById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteByIdNever() {
        //Given

        //When
        service.deleteById(1l);
        service.deleteById(1l);

        //Then
        then(specialtyRepository).should(atLeastOnce()).deleteById(anyLong());
        then(specialtyRepository).should(never()).deleteById(5l);
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void delete() {
        //Given

        //When
        service.delete(new Speciality());

        //Then
        then(specialtyRepository).should().delete(any(Speciality.class));
        then(specialtyRepository).shouldHaveNoMoreInteractions();

    }
}
