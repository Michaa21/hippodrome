import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class HorseTest {
    @Test
    void constructor_nullName_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 10, 5);
        });
        assertEquals("Name cannot be null.", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "   \t  "})
    void constructor_blankName_throwsIllegalArgumentException(String blankName) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(blankName, 10, 5);
        });
        assertEquals("Name cannot be blank.", ex.getMessage());
    }

    @Test
    void constructor_negativeSpeed_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("HorseName", -1, 5);
        });
        assertEquals("Speed cannot be negative.", ex.getMessage());
    }

    @Test
    void constructor_negativeDistance_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("HorseName", 10, -5);
        });
        assertEquals("Distance cannot be negative.", ex.getMessage());
    }

    @Test
    void getName_returnsNamePassedToConstructor() {
        String expectedName = "Thunder";
        Horse horse = new Horse(expectedName, 10, 5);

        String actualName = horse.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void getSpeed_returnsSpeedPassedToConstructor() {
        double expectedSpeed = 15.5;
        Horse horse = new Horse("Lightning", expectedSpeed, 10);

        double actualSpeed = horse.getSpeed();

        assertEquals(expectedSpeed, actualSpeed);
    }

    @Test
    void getDistance_returnsDistancePassedToConstructor() {
        double expectedDistance = 20;
        Horse horse = new Horse("Storm", 12, expectedDistance);

        double actualDistance = horse.getDistance();

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    void getDistance_returnsZeroIfCreatedWithTwoParamsConstructor() {
        Horse horse = new Horse("Blaze", 15);

        double actualDistance = horse.getDistance();

        assertEquals(0, actualDistance);
    }

    @Test
    void move_callsGetRandomDoubleWithCorrectParameters() {
        Horse horse = new Horse("Буцефал", 10, 5);

        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);

            horse.move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9), times(1));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9})
    void move_correctlyUpdatesDistance(double mockedRandom) {
        double initialDistance = 10;
        double speed = 20;
        Horse horse = new Horse("Буцефал", speed, initialDistance);

        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(mockedRandom);

            horse.move();

            double expectedDistance = initialDistance + speed * mockedRandom;

            assertEquals(expectedDistance, horse.getDistance(),
                    "Distance должен обновиться по формуле: distance + speed * randomValue");
        }
    }
}