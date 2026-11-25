package pl.edu.vistula.firstprojectjavaspring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FirstProjectJavaSpringApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void helloEndpointReturnsText() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Vistula, in my first Spring controller."));
    }

    @Test
    void greetingEndpointRendersView() throws Exception {
        mockMvc.perform(get("/greeting").param("name", "Vistula"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, Vistula!")))
                .andExpect(content().string(containsString("/images/vistula.png")));
    }

}
