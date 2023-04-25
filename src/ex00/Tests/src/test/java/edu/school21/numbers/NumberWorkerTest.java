package edu.school21.numbers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {
    private final NumberWorker numberWorker = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = { 2, 113, 9834497 })
    void isPrimeTrue(int num) {
        assertTrue(numberWorker.isPrime(num));
    }
    @ParameterizedTest
    @ValueSource(ints = { 4 , 112, 9834498 })
    void isPrimeFalse(int num) {
        assertFalse(numberWorker.isPrime(num));
    }
    @ParameterizedTest
    @ValueSource(ints = { -1, 0, 1})
    void isPrimeException(int num) {
        assertThrowsExactly(NumberWorker.IllegalNumberException.class , () -> {numberWorker.isPrime(num);});
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/data.csv", numLinesToSkip = 1)
    void digitSumTest(int input, int result) {
        assertEquals(numberWorker.digitSum(input), result);
    }
}



