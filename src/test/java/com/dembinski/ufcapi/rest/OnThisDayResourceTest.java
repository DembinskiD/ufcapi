package com.dembinski.ufcapi.rest;

import com.dembinski.ufcapi.mapper.FightMapperImpl;
import com.dembinski.ufcapi.repository.FightRepository;
import com.dembinski.ufcapi.service.FightService;
import com.dembinski.ufcapi.source.FightDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {OnThisDayResource.class, FightMapperImpl.class, FightService.class, FightRepository.class})
@MockBeans( value = {
        @MockBean(FightRepository.class)
})
class OnThisDayResourceTest {

    @Autowired
    private OnThisDayResource onThisDayResource;

    @Test
    void getListOfFights() {
        OnThisDayResource OtdSpy = Mockito.spy(onThisDayResource);

        Mockito.doReturn(getListOfTestFights()).when(OtdSpy).getAllFights();
        Mockito.doReturn(LocalDate.of(2023, 9, 13)).when(OtdSpy).getThisDay();

        List<FightDto> fights = OtdSpy.whatHappenedOnThisDay();

        assertEquals(2, fights.size());
        assertEquals(2, fights.stream().filter(fight -> fight.getDate().equals(LocalDate.of(2023, 9, 13))).count());
    }

    private List<FightDto> getListOfTestFights() {
        return List.of(
                createFight("2023-09-13"),
                createFight("2023-09-13"),
                createFight("2023-09-14"),
                createFight("2023-09-15"),
                createFight("2023-09-16"));
    }

    private FightDto createFight(String date) {
        return FightDto.builder()
                .date(LocalDate.parse(date, DateTimeFormatter.ISO_DATE))
                .build();
    }
}