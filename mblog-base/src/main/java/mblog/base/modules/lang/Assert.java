/*
+--------------------------------------------------------------------------
|   mtons [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.base.modules.lang;

import mblog.base.modules.exception.MtonsException;

public class Assert {
    public static void isTrue(boolean expression, int errorCode) {
        if (!expression) {
            throw new MtonsException(errorCode);
        }
    }

    public static void isFalse(boolean expression, int errorCode) {
        if (expression) {
            throw new MtonsException(errorCode);
        }
    }

    public static void isNull(Object object, int errorCode) {
        isTrue(object == null, errorCode);
    }

    public static void notNull(Object object, int errorCode) {
        isFalse(object == null, errorCode);
    }

    public static void instanceOf(Object object, Class<?> c, int errorCode) {
        isTrue(c.isInstance(object), errorCode);
    }

    public static void notInstanceOf(Object object, Class<?> c, int errorCode) {
        isFalse(c.isInstance(object), errorCode);
    }

    public static void gt(long x, long y, int errorCode) {
        isTrue(x > y, errorCode);
    }

    public static void ge(long x, long y, int errorCode) {
        isTrue(x >= y, errorCode);
    }

    public static void lt(long x, long y, int errorCode) {
        isTrue(x < y, errorCode);
    }

    public static void le(long x, long y, int errorCode) {
        isTrue(x <= y, errorCode);
    }

    public static void eq(long x, long y, int errorCode) {
        isTrue(x == y, errorCode);
    }

    public static void ne(long x, long y, int errorCode) {
        isTrue(x != y, errorCode);
    }

    public static <T> void eq(Comparable<T> x, T y, int errorCode) {
        isTrue(x.compareTo(y) == 0, errorCode);
    }

    public static <T> void ne(Comparable<T> x, T y, int errorCode) {
        isTrue(x.compareTo(y) != 0, errorCode);
    }

    public static <T> void gt(Comparable<T> x, T y, int errorCode) {
        isTrue(x.compareTo(y) == 1, errorCode);
    }

    public static <T> void ge(Comparable<T> x, T y, int errorCode) {
        isTrue(x.compareTo(y) != -1, errorCode);
    }

    public static <T> void lt(Comparable<T> x, T y, int errorCode) {
        isTrue(x.compareTo(y) == -1, errorCode);
    }

    public static <T> void le(Comparable<T> x, T y, int errorCode) {
        isTrue(x.compareTo(y) != 1, errorCode);
    }

}
