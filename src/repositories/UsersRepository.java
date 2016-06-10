package repositories;

import models.User;

public class UsersRepository extends BaseRepository<User> {

    public UsersRepository() {
        super();
        classT = User.class;
    }

    public User getByUsernameAndPassword(String username, String password) throws InstantiationException, IllegalAccessException {
        return this.getAll().stream().filter((User) -> User.getUsername().equals(username) && User.getPassword().equals(password)).findFirst().orElse(null);
    }
}