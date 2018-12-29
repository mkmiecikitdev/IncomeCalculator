package kmiecik.michal.earningscalculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AcceptanceTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnNumericValue() throws Exception {
        this.mockMvc.perform(get("/calculate?dailyRateGross=100&country=de")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.income").isNotEmpty());
    }


    @Test
    public void shouldReturnError() throws Exception {
        this.mockMvc.perform(get("/calculate?dailyRateGross=100&country=rus")).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorReason").value("COUNTRY_NOT_SUPPORTED"))
                .andExpect(jsonPath("$.message").value("RUS"));
    }

}
