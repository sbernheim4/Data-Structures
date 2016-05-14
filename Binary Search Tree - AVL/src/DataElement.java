
public class DataElement<T> implements Comparable<DataElement<T>> {
    private T value;
    private Integer weight;

    public DataElement(T t, int i) {
        this.value = t;
        this.weight = new Integer(i);
    }

    @Override
    public int compareTo(DataElement<T> o) {
        return this.weight.compareTo(o.weight);
    }

    public String toString() {
        return value.toString();
    }
}
