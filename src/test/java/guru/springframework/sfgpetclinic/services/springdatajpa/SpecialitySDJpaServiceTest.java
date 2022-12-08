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
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock(lenient = true)
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

    @Test
    void testDoThrow(){
        doThrow(new RuntimeException("Boom")).when(specialtyRepository).delete(any(Speciality.class));

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        verify(specialtyRepository).delete(any(Speciality.class));
    }

    @Test
    void testFindByIdThrows() {
        //GIVEN
        given(specialtyRepository.findById(1L)).willThrow(new RuntimeException("Boom"));

        //WHEN
        assertThrows(RuntimeException.class, () -> specialtyRepository.findById(1L));

        //THEN
        then(specialtyRepository).should().findById(1L);
    }

    @Test
    void testDeleteBDD(){
        willThrow(new RuntimeException("Boom")).given(specialtyRepository).delete(any(Speciality.class));

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void testSaveLambda(){
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription(MATCH_ME);

        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(1L);

        //Need Mock to only return on mach MATCH_ME string
        given(specialtyRepository.save(argThat(argument ->  argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpeciality);
        //When
        Speciality returnedSpeciality = service.save(speciality);

        //Then
        assertThat(returnedSpeciality.getId()).isEqualTo(1L);
    }

    @Test
    void testSaveLambdaNoMatch(){
        //given
        final String MATCH_ME = "MATCH_ME";
        Speciality speciality = new Speciality();
        speciality.setDescription("Not a match");

        Speciality savedSpeciality = new Speciality();
        savedSpeciality.setId(1L);

        //Need Mock to only return on mach MATCH_ME string
        given(specialtyRepository.save(argThat(argument ->  argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpeciality);
        //When
        Speciality returnedSpeciality = service.save(speciality);

        //Then
        assertNull(returnedSpeciality);
    }
}
