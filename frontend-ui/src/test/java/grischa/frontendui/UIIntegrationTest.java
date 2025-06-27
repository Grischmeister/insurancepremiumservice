package grischa.frontendui;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UIIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSubmitFormE2E() throws Exception {
        mockMvc.perform(post("/calculate")
                        .param("zipcode", "10115")
                        .param("carType", "SUV")
                        .param("kilometers", "12000"))
                .andExpect(status().isOk())
                .andExpect(view().name("form"))
                .andExpect(model().attributeExists("result"));
    }
}
