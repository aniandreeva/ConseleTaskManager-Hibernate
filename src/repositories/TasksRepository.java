package repositories;

import java.util.ArrayList;
import java.util.stream.Collectors;

import models.Task;

public class TasksRepository extends BaseRepository<Task> {

    public TasksRepository() {
        super();
        classT = Task.class;
    }

    public ArrayList<Task> getTasksByUserId(int id) {
        ArrayList<Task> tasks = this.getAll().stream().filter(t -> t.getUser().getId() == id).collect(Collectors.toCollection(ArrayList<Task>::new));
        return tasks;
    }

    public Task getByTitleAndContent(String title, String content) {
        return this.getAll().stream().filter((Task) -> Task.getTitle().equals(title) && Task.getContent().equals(content)).findFirst().orElse(null);
    }
}