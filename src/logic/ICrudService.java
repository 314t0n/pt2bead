package logic;

import java.util.List;

public interface ICrudService<T extends IEntity> {

    public List<T> readAll();

    public void create(T t);

    public T read(Integer id);

    public void update(T t);

    public void delete(T t);

    public int rowCount();
}
