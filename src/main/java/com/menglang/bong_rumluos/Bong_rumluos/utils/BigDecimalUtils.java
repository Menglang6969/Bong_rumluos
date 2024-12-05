package com.menglang.bong_rumluos.Bong_rumluos.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {
    public static BigDecimal convertToZeroIfNegative(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return value;
    }
}
