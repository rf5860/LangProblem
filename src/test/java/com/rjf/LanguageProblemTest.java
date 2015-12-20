package com.rjf;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class LanguageProblemTest {
    public static final String TEXT_TXT = "TEXT.txt";
    public static final String SECOND_TEXT_TXT = "SECOND_TEXT.txt";
    private String TEST_LANGUAGE = "FAKE";
    private String FRUIT_LANGUAGE = "FRUITS";
    private String[] GOOD_WORDS = {"CAT", "DOG", "HORSE", "ZEBRA", "PYTHON"};

    private String getPath(String fileName) {
        return getClass().getResource("/" + fileName).getPath();
    }

    private String[] getDefaultLanguages() {
        String fake = getPath("FAKE.1");
        String fake2 = getPath("FAKE.2");
        String fruits = getPath("FRUITS.1");
        String mix = getPath("MIX.1");
        return new String[]{fake, fake2, fruits, mix};
    }

    @Test
    public void testWordsCorrect() {
        LanguageProblem lp = defaultLanguage();
        assertThat(lp.getLanguageWords(TEST_LANGUAGE), containsInAnyOrder(GOOD_WORDS));
    }

    private LanguageProblem defaultLanguage() {
        LanguageProblem lp = new LanguageProblem();
        lp.addLanguages(getDefaultLanguages());
        return lp;
    }

    @Test
    public void testLanguagesDetected() {
        LanguageProblem lp = defaultLanguage();
        assertThat(lp.determineLanguage(getPath(TEXT_TXT)), is(TEST_LANGUAGE));
        assertThat(lp.determineLanguage(getPath(SECOND_TEXT_TXT)), is(FRUIT_LANGUAGE));
    }
}
