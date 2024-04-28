package exercise.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
// END
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;

    private Task testTask;

    @BeforeEach
    public void setUp() {
        testTask = new Task();
        testTask.setTitle("test title");
        testTask.setDescription("test description");
        taskRepository.save(testTask);
    }

    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    // BEGIN
    @Test
    public void testShow() throws Exception {
        mockMvc.perform(get("/tasks/{id}", testTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(om.writeValueAsString(testTask)));
    }

    @Test
    public void testCreate() throws Exception {
        var task = new Task();
        task.setTitle("test title");
        task.setDescription("test description");

        var request = post("/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(task));

        mockMvc.perform(request)
            .andExpect(status().isCreated());
    }

    @Test
    public void testUpdate() throws Exception {
        var task = Instancio.of(Task.class)
            .ignore(Select.field(Task::getId))
            .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
            .supply(Select.field(Task::getDescription), () -> faker.lorem().paragraph())
            .create();
        taskRepository.save(task);

        var data = new HashMap<>();
        data.put("title", "newTitle");
        data.put("description", "newDescription");

        var request = put("/tasks/" + task.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(data));

        mockMvc.perform(request)
            .andExpect(status().isOk());

        task = taskRepository.findById(task.getId()).get();
        assertThat(task.getTitle()).isEqualTo(("newTitle"));
        assertThat(task.getDescription()).isEqualTo(("newDescription"));

        taskRepository.delete(task);
    }

    @Test
    public void testDelete() throws Exception {

        var currentID = testTask.getId();

        mockMvc.perform(delete("/tasks/{id}", testTask.getId()))
            .andExpect(status().isOk());

        assertThat(taskRepository.findById(currentID)).isEmpty();
    }
    // END
}
