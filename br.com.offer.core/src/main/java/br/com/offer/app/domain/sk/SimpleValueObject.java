package br.com.offer.app.domain.sk;

import java.io.Serializable;
import java.util.Objects;

public abstract class SimpleValueObject<T extends Serializable> implements Serializable {

    private transient int hash;

    public abstract T getValue();

    @Override
    public final int hashCode() {
        if (hash == 0)
            hash = Objects.hash(getValue());
        return hash;
    }

    @Override
    public final boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || !getClass().equals(obj.getClass()))
            return false;

        @SuppressWarnings("unchecked")
        SimpleValueObject<T> other = (SimpleValueObject<T>) obj;

        if (getValue() instanceof String) {
            String currentValue = (String) getValue();
            String otherValue = (String) other.getValue();
            return currentValue.equalsIgnoreCase(otherValue);
        }

        return Objects.equals(getValue(), other.getValue());
    }

    @Override
    public final String toString() {
        return getClass().getSimpleName() + " [value=" + getValue() + "]";
    }

    /**
     * Call, directly, the <code>toString()</code> from the internal value.
     *
     * @return the internal value as string.
     */
    public String asString() {
        return getValue().toString();
    }

}
