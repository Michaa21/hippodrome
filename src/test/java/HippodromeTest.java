import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class HippodromeTest {

    // Конструктор: проверка на null
    @Test
    void constructor_throwsIllegalArgumentException_whenHorsesIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    // Конструктор: проверка на пустой список
    @Test
    void constructor_throwsIllegalArgumentException_whenHorsesIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(Collections.emptyList()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    // getHorses возвращает тот же список (по содержимому и порядку)
    @Test
    void getHorses_returnsSameListWithSameOrder() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse" + i, i + 1));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        List<Horse> returnedHorses = hippodrome.getHorses();

        // Проверяем, что список содержит те же объекты
        assertEquals(horses.size(), returnedHorses.size());
        for (int i = 0; i < horses.size(); i++) {
            assertSame(horses.get(i), returnedHorses.get(i));
        }
    }

    // move вызывает метод move у всех лошадей
    @Test
    void move_callsMoveOnAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse horse = new Horse("Horse" + i, 10);
            Horse spyHorse = spy(horse); // создаём spy для реального объекта
            horses.add(spyHorse);
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for (Horse horse : horses) {
            verify(horse, times(1)).move();
        }
    }

    // getWinner возвращает лошадь с максимальной дистанцией
    @Test
    void getWinner_returnsHorseWithMaxDistance() {
        Horse horse1 = new Horse("Horse1", 10, 100);
        Horse horse2 = new Horse("Horse2", 10, 200);
        Horse horse3 = new Horse("Horse3", 10, 150);

        List<Horse> horses = List.of(horse1, horse2, horse3);

        Hippodrome hippodrome = new Hippodrome(horses);

        Horse winner = hippodrome.getWinner();

        assertSame(horse2, winner);
    }
}