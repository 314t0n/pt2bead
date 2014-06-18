package logic;

public interface IEntity {

    public Long getId();

    public void setId(Long id);

    public Object get(int columnIndex);

    public void set(int columnIndex, Object value);

    public String[] getPropertyNames();

}
