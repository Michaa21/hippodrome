import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    @Disabled("Disabled to avoid slowing down all tests. Run manually if needed.")
    public void main_shouldCompleteWithin22Seconds() throws Exception {
        // Запускаем метод main без аргументов
        Main.main(new String[]{});
    }
}