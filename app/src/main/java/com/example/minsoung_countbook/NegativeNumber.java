package com.example.minsoung_countbook;

/**
 * An exception for negative number since
 * negative numbers are not allowed for values of
 * counters.
 */
class NegativeNumber extends Throwable {
    public NegativeNumber(String s) {
        super(s);
    }
}
