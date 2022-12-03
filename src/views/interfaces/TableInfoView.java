package views.interfaces;

public interface TableInfoView<T, E> extends InfoView<E> {
    void setTableInfo(T info);

    Integer getSelectedID();

    void resetSelectedID();
}
