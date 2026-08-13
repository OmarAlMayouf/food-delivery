package io.github.omaralmayouf.food_delivery.shared.error;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTest {

    private Properties load(String fileName) throws IOException {
        try (InputStream stream = getClass().getResourceAsStream(fileName)) {
            Assertions.assertNotNull(stream);
            try (InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {

                Properties properties = new Properties();
                properties.load(reader);
                return properties;
            }
        }
    }

    @Test
    void shouldHaveTheSameKeysInEveryLanguage() throws IOException {
        Set<String> englishKeys = load("/messages.properties").stringPropertyNames();
        Set<String> arabicKeys = load("/messages_ar.properties").stringPropertyNames();

        assertThat(arabicKeys).containsExactlyInAnyOrderElementsOf(englishKeys);
    }

    @Test
    void shouldNotHaveBlankTranslations() throws IOException {
        assertThat(load("/messages.properties").values())
                .noneMatch(value -> value.toString().isBlank());

        assertThat(load("/messages_ar.properties").values())
                .noneMatch(value -> value.toString().isBlank());
    }

}
