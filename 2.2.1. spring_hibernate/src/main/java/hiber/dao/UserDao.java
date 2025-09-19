package hiber.dao;

import hiber.model.User;
import java.util.List;

public interface UserDao {
   void add(User user);
    User getById(Long id);
    List<User> listUsers();
    User findUserByCarModelAndSeries(String model, int series);
}
