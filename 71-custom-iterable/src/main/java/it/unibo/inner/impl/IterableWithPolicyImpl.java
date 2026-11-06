package it.unibo.inner.impl;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private final List<T> array;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(final T[] elements) {
        this(elements, test -> true);
    }

    public IterableWithPolicyImpl(final T[] array, final Predicate<T> filter) {
        this.array = List.of(array);
        this.filter = filter;
    }

    @Override
    public void setIterationPolicy(final Predicate<T> filter) {
        this.filter = filter;
    }

    public class IteratorImpl implements Iterator<T> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            while (index < array.size()) {
                final T elem = array.get(index);
                if (filter.test(elem)) {
                    return true;
                }
                index++;
            }
            return false;
        }
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is not another element");
            }
            return array.get(index++);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorImpl();
    }
}