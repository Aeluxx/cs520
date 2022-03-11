package triangle;

import org.junit.Test;
import static org.junit.Assert.*;

import static triangle.Triangle.Type;
import static triangle.Triangle.Type.*;

/**
 * Test class for the Triangle implementation.
 */
public class TriangleTest_Mutant_Detection {

    @Test
    public void constructorTest() {
        triangle.Triangle a = new triangle.Triangle();
        assertNotNull(a);
    }

    @Test
    public void trianZeroConditional() {
        Type line = Triangle.classify(1, 2, 3);
        Type cTooLong = Triangle.classify(1, 2, 4);
        Type bTooLong = Triangle.classify(1, 4, 2);
        Type aTooLong = Triangle.classify(4, 1, 2);

        assertEquals(INVALID, cTooLong);
        assertEquals(INVALID, aTooLong);
        assertEquals(INVALID, bTooLong);
        assertEquals(INVALID, line);
    }

    @Test
    public void trianNonZeroConditional() {
        Type abSameInvalid = Triangle.classify(1, 1, 2);
        Type abSameValid = Triangle.classify(2, 2, 3);
        Type bcSameInvalid = Triangle.classify(2, 1, 1);
        Type bcSameValid = Triangle.classify(3, 2, 2);
        Type acSameInvalid = Triangle.classify(1, 2, 1);
        Type acSameValid = Triangle.classify(2, 3, 2);

        assertEquals(abSameValid, ISOSCELES);
        assertEquals(bcSameValid, ISOSCELES);
        assertEquals(acSameValid, ISOSCELES);
        assertEquals(abSameInvalid, INVALID);
        assertEquals(bcSameInvalid, INVALID);
        assertEquals(acSameInvalid, INVALID);
    }

    @Test
    public void invlidTest() {
        Type Abc = Triangle.classify(-1, 1, 1);
        Type ABC = Triangle.classify(-1, -1, -1);
        Type abc = Triangle.classify(1, 1, 1);
        Type aBc = Triangle.classify(1, -1, 1);
        Type abC = Triangle.classify(1, 1, -1);
        Type ABc = Triangle.classify(-1, -1, 1);
        Type AbC = Triangle.classify(-1, 1, -1);
        Type aBC = Triangle.classify(1, -1, -1);
        Type expected = INVALID;

        assertEquals(expected, Abc);
        assertEquals(expected, aBc);
        assertEquals(expected, abC);
        assertEquals(expected, ABc);
        assertEquals(expected, aBC);
        assertEquals(expected, AbC);
        assertEquals(expected, aBC);
    }

    @Test
    public void equilateral() {
        Type actual = Triangle.classify(10, 10, 10);
        Type expected = EQUILATERAL;
        assertEquals(actual, expected);
    }

    @Test
    public void invalidNegative() {
        Type actualNegative = Triangle.classify(-1, 10, 10);
        Type expected = INVALID;
        assertEquals(actualNegative, expected);
    }

    @Test
    public void invalidZero() {
        Type actualZero = Triangle.classify(0, 0, 0);
        Type expected = INVALID;
        assertEquals(actualZero, expected);

    }

    @Test
    public void invalidImpossible() {
        Type actualImpossible = Triangle.classify(1, 2, 10);
        Type expected = INVALID;
        assertEquals(actualImpossible, expected);
    }


    @Test
    public void scalene() {
        Type actual = Triangle.classify(5, 6, 7);
        Type expected = SCALENE;
        assertEquals(actual, expected);
    }

    @Test
    public void isoscelesAB() {
        Type actualAB = Triangle.classify(5, 5, 6);
        Type expected = ISOSCELES;
        assertEquals(actualAB, expected);
    }

    @Test
    public void isoscelesBC() {
        Type actualBC = Triangle.classify(6, 5, 5);
        Type expected = ISOSCELES;
        assertEquals(actualBC, expected);
    }

    @Test
    public void isoscelesAC() {
        Type actualAC = Triangle.classify(5, 6, 5);
        Type expected = ISOSCELES;
        assertEquals(actualAC, expected);
    }

    @Test
    public void barelyZeroValid() {
        Type aBarelyValid = Triangle.classify(1, 4, 4);
        Type bBarelyValid = Triangle.classify(4, 1, 4);
        Type cBarelyValid = Triangle.classify(4, 4, 1);
        assertEquals(aBarelyValid, ISOSCELES);
        assertEquals(bBarelyValid, ISOSCELES);
        assertEquals(cBarelyValid, ISOSCELES);
        Type aBarelyInvalid = Triangle.classify(0, 4, 4);
        Type bBarelyInvalid = Triangle.classify(4, 0, 4);
        Type cBarelyInvalid = Triangle.classify(4, 4, 0);
        Type abBarelyInvalid = Triangle.classify(0, 0, 4);
        Type acBarelyInvalid = Triangle.classify(0, 4, 0);
        Type bcBarelyInvalid = Triangle.classify(4, 0, 0);
        assertEquals(aBarelyInvalid, INVALID);
        assertEquals(bBarelyInvalid, INVALID);
        assertEquals(cBarelyInvalid, INVALID);
        assertEquals(acBarelyInvalid, INVALID);
        assertEquals(bcBarelyInvalid, INVALID);
        assertEquals(abBarelyInvalid, INVALID);
    }

    @Test
    public void trianZeroMutations() {
        Type abProduct = Triangle.classify(2, 4, 7);
        Type acProduct = Triangle.classify(2, 7, 4);
        Type bcProduct = Triangle.classify(7, 2, 4);
        assertEquals(abProduct, INVALID);
        assertEquals(acProduct, INVALID);
        assertEquals(bcProduct, INVALID);
        Type abSumEqual = Triangle.classify(2, 4, 6);
        Type acSumEqual = Triangle.classify(2, 6, 4);
        Type bcSumEqual = Triangle.classify(6, 2, 4);
        assertEquals(abSumEqual, INVALID);
        assertEquals(acSumEqual, INVALID);
        assertEquals(bcSumEqual, INVALID);
        Type abModulo = Triangle.classify(8, 2, 7);
        Type acModulo = Triangle.classify(8, 7, 2);
        Type bcModulo = Triangle.classify(7, 8, 2);
        assertEquals(abModulo, SCALENE);
        assertEquals(acModulo, SCALENE);
        assertEquals(bcModulo, SCALENE);
    }

    @Test
    public void trianNotZeroMutations() {
        Type abProduct = Triangle.classify(3, 3, 7);
        Type acProduct = Triangle.classify(3, 7, 3);
        Type bcProduct = Triangle.classify(7, 3, 3);
        assertEquals(abProduct, INVALID);
        assertEquals(acProduct, INVALID);
        assertEquals(bcProduct, INVALID);
    }
}
