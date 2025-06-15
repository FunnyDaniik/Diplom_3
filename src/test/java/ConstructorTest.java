import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.BaseTest;
import ru.praktikum.MainPage;

import static org.junit.Assert.assertEquals;

public class ConstructorTest extends BaseTest {
    private MainPage mainPage;

    @Override
    public void setUp() {
        super.setUp();
        mainPage = new MainPage(driver);
    }

    @Test
    @DisplayName("Переход к разделу 'Булки'")
    public void testBunsSection() {
        mainPage.clickSaucesSection();
        mainPage.clickBunsSection();
        assertEquals("Булки", mainPage.getSelectedSectionText());
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    public void testSaucesSection() {
        mainPage.clickSaucesSection();
        assertEquals("Соусы", mainPage.getSelectedSectionText());
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    public void testFillingsSection() {
        mainPage.clickFillingsSection();
        assertEquals("Начинки", mainPage.getSelectedSectionText());
    }
}
