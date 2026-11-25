package pl.edu.vistula.firstrestapispring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FirstRestApiSpringApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void fullCrudAndExceptionFlow() throws Exception {
        // 1. Create product (POST -> 201 Created)
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"First product\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("First product"));

        // 2. Find product by id (GET /{id} -> 200 OK)
        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("First product"));

        // 3. Update product (PUT /{id} -> 200 OK)
        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated product\",\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated product"));

        // 4. Find all products (GET -> 200 OK)
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Updated product"));

        // 5. Delete product (DELETE /{id} -> 204 No Content)
        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNoContent());

        // 6. Find deleted product (GET /{id} -> 404 Not Found with ErrorMessageResponse)
        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product with 1 not found"));

        // 7. OpenAPI / Swagger docs endpoint
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("Products API for Vistula"));
    }

}
