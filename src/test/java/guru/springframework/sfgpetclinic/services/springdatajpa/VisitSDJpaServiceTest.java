package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    @Test
    void findAll() {
        //Given
        Set<Visit> visits = new HashSet<>();
        visits.add(new Visit());
        given(visitRepository.findAll()).willReturn(visits);

        //When
        Set<Visit> foundVisits = service.findAll();

        //Then
        assertThat(foundVisits).hasSize(1);
        then(visitRepository).should().findAll();
        then(visitRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void findById() {
        //Given
        Visit visit = new Visit();
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));

        //When
        Visit foundVisit = service.findById(1L);

        //Then
        assertThat(foundVisit).isNotNull();
        then(visitRepository).should().findById(anyLong());
        then(visitRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void save() {
        //Given
        Visit visit = new Visit();
        given(visitRepository.save(any(Visit.class))).willReturn(visit);

        //When
        Visit savedVisit = service.save(visit);

        //Then
        assertThat(savedVisit).isNotNull();
        then(visitRepository).should().save(any(Visit.class));
        then(visitRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void delete() {
        //Given
        Visit visit = new Visit();

        //When
        service.delete(visit);

        //Then
        then(visitRepository).should().delete(any());
        then(visitRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteById() {
        //Given

        //When
        service.deleteById(1L);

        //Then
        then(visitRepository).should().deleteById(anyLong());
        then(visitRepository).shouldHaveNoMoreInteractions();
    }
}
