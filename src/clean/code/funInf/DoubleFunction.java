package clean.code.funInf;

import java.util.function.Function;

@FunctionalInterface
public interface DoubleFunction<R, T> extends Function<T, R> {
    @Override
    R apply(T t);
}
