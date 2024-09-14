package com.kneelawk.minecatutils.fixes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

public class BlockingConcurrentIterationArrayList<T> implements List<T> {
    private final List<T> list = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Set<ConcurrentLinkedQueue<T>> iterators = new LinkedHashSet<>();
    private final ReentrantReadWriteLock iteratorsLock = new ReentrantReadWriteLock();

    private final boolean log;

    public BlockingConcurrentIterationArrayList(boolean log) {this.log = log;}

    @Override
    public int size() {
        lock.readLock().lock();
        try {
            return list.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        lock.readLock().lock();
        try {
            return list.isEmpty();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean contains(Object o) {
        lock.readLock().lock();
        try {
            return list.contains(o);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        ConcurrentLinkedQueue<T> queue;
        lock.readLock().lock();
        try {
            queue = new ConcurrentLinkedQueue<>(list);
        } finally {
            lock.readLock().unlock();
        }

        iteratorsLock.writeLock().lock();
        try {
            iterators.add(queue);
        } finally {
            iteratorsLock.writeLock().unlock();
        }

        return new Iter<>(queue, this::finishIterator);
    }

    @Override
    public @NotNull Object[] toArray() {
        lock.readLock().lock();
        try {
            return list.toArray();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public @NotNull <T1> T1[] toArray(@NotNull T1[] a) {
        lock.readLock().lock();
        try {
            return list.toArray(a);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean add(T t) {
        lock.writeLock().lock();
        try {
            list.add(t);
        } finally {
            lock.writeLock().unlock();
        }

        iteratorsLock.readLock().lock();
        try {
            if (log) {
                // logging means we check who is adding things after iteration has begun
                if (!iterators.isEmpty()) {
                    MCUFLog.LOG.error(
                        "[Minecat Utils: Fixes] Someone tried to register a listener after listener iteration had already begun. Listener: {}",
                        t, new RuntimeException("Stack Trace"));
                }
            }

            for (ConcurrentLinkedQueue<T> queue : iterators) {
                queue.add(t);
            }
        } finally {
            iteratorsLock.readLock().unlock();
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        lock.writeLock().lock();
        try {
            return list.remove(o);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        lock.readLock().lock();
        try {
            return list.containsAll(c);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        lock.writeLock().lock();
        try {
            list.addAll(c);
        } finally {
            lock.writeLock().unlock();
        }

        iteratorsLock.readLock().lock();
        try {
            for (ConcurrentLinkedQueue<T> queue : iterators) {
                queue.addAll(c);
            }
        } finally {
            iteratorsLock.readLock().unlock();
        }

        return true;
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        lock.writeLock().lock();
        try {
            list.addAll(index, c);
        } finally {
            lock.writeLock().unlock();
        }

        iteratorsLock.readLock().lock();
        try {
            if (log) {
                // logging means we check who is adding things after iteration has begun
                if (!iterators.isEmpty()) {
                    MCUFLog.LOG.error(
                        "[Minecat Utils: Fixes] Someone tried to register a listener after listener iteration had already begun.",
                        new RuntimeException("Stack Trace"));
                }
            }

            for (ConcurrentLinkedQueue<T> queue : iterators) {
                queue.addAll(c);
            }
        } finally {
            iteratorsLock.readLock().unlock();
        }

        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        lock.writeLock().lock();
        try {
            return list.removeAll(c);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        lock.writeLock().lock();
        try {
            return list.retainAll(c);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void clear() {
        lock.writeLock().lock();
        try {
            list.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public T get(int index) {
        lock.readLock().lock();
        try {
            return list.get(index);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public T set(int index, T element) {
        lock.writeLock().lock();
        try {
            return list.set(index, element);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void add(int index, T element) {
        lock.writeLock().lock();
        try {
            list.add(index, element);
        } finally {
            lock.writeLock().unlock();
        }

        iteratorsLock.readLock().lock();
        try {
            if (log) {
                // logging means we check who is adding things after iteration has begun
                if (!iterators.isEmpty()) {
                    MCUFLog.LOG.error(
                        "[Minecat Utils: Fixes] Someone tried to register a listener after listener iteration had already begun.",
                        new RuntimeException("Stack Trace"));
                }
            }

            for (ConcurrentLinkedQueue<T> queue : iterators) {
                queue.add(element);
            }
        } finally {
            iteratorsLock.readLock().unlock();
        }
    }

    @Override
    public T remove(int index) {
        lock.writeLock().lock();
        try {
            return list.remove(index);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public int indexOf(Object o) {
        lock.readLock().lock();
        try {
            return list.indexOf(o);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        lock.readLock().lock();
        try {
            return list.lastIndexOf(o);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public @NotNull ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private void finishIterator(ConcurrentLinkedQueue<T> q) {
        iteratorsLock.writeLock().lock();
        try {
            iterators.remove(q);
        } finally {
            iteratorsLock.writeLock().unlock();
        }
    }

    private static class Iter<T> implements Iterator<T> {
        private final ConcurrentLinkedQueue<T> queue;
        private final Consumer<ConcurrentLinkedQueue<T>> onFinish;

        private Iter(ConcurrentLinkedQueue<T> queue, Consumer<ConcurrentLinkedQueue<T>> onFinish) {
            this.queue = queue;
            this.onFinish = onFinish;
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            T res = queue.remove();

            if (queue.isEmpty()) {
                onFinish.accept(queue);
            }

            return res;
        }
    }
}
