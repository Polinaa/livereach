package com.example.livereach;

import com.example.livereach.api.LiveReachExternalAPI;
import com.example.livereach.model.ContentAvailableForEmbedResponse;
import com.example.livereach.repository.AvailabilityRepository;
import com.example.livereach.scheduler.ContentAvailableForEmbedScheduler;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
class LivereachApplicationTests {

    private static final Integer EMBED_ID = 1234;
    private static final String PRODUCT_ID = "productTest";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LiveReachExternalAPI externalAPI;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @SpyBean
    private ContentAvailableForEmbedScheduler scheduler;

    @Test
    void shouldReturnAvailabilityMap() throws Exception {
        mvc.perform(get("/api/availability?embedId=1234&productId=productTest&minTime=1595683643&maxTime=1595700343&interval=60"))
           .andExpect(status().isOk())
           .andExpect(content().json("{\"1595687243\":false,\"1595690843\":false,\"1595694443\":true,\"1595698043\":true,\"1595700343\":true}"));
    }

    @Test
    void shouldReturnEmpty_whenNoDataFound() throws Exception {
        mvc.perform(get("/api/availability?embedId=1234&productId=productTest2&minTime=1595683643&maxTime=1595700343&interval=60"))
           .andExpect(status().isOk())
           .andExpect(content().json("{}"));
    }

    @Test
    void shouldMethodNotAllowed_whenPostIsUsed() throws Exception {
        mvc.perform(post("/api/availability?embedId=1234&productId=productTest&minTime=1595683643&maxTime=1595700343&interval=60"))
           .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void shouldReturnBadRequest_whenNoParams() throws Exception {
        mvc.perform(get("/api/availability"))
           .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenEmptyProductId() throws Exception {
        mvc.perform(get("/api/availability?embedId=1234&productId=&minTime=1595683643&maxTime=1595700343&interval=60"))
           .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenNoEmbedId() throws Exception {
        mvc.perform(get("/api/availability?productId=productTest&minTime=1595683643&maxTime=1595700343&interval=60"))
           .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenNoInterval() throws Exception {
        mvc.perform(get("/api/availability?embedId=1234&productId=productTest&minTime=1595683643&maxTime=1595700343"))
           .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenNoMin() throws Exception {
        mvc.perform(get("/api/availability?embedId=1234&productId=productTest&maxTime=1595700343&interval=60"))
           .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenNoMax() throws Exception {
        mvc.perform(get("/api/availability?embedId=1234&productId=productTest&minTime=1595683643&interval=60"))
           .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnOk_intervalIsMinimum() throws Exception {
        mvc.perform(get("/api/availability?embedId=1234&productId=productTest&minTime=1595683643&maxTime=1595700343&interval=30"))
           .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequest_intervalIsLessThanMinimum() throws Exception {
        mvc.perform(get("/api/availability?embedId=1234&productId=productTest&minTime=1595683643&maxTime=1595700343&interval=29"))
           .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnOk_intervalIsMaximum() throws Exception {
        mvc.perform(get("/api/availability?embedId=1234&productId=productTest&minTime=1595683643&maxTime=1595700343&interval=1440"))
           .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequest_intervalIsMoreThanMonth() throws Exception {
        mvc.perform(get("/api/availability?embedId=1234&productId=productTest&minTime=1595683643&maxTime=1598378643&interval=1441"))
           .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenMinTimeIsGreaterThanMaxTime() throws Exception {
        mvc.perform(get("/api/availability?embedId=1234&productId=productTest&minTime=1595700343&maxTime=1595683643&interval=60"))
           .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenTimeRangeIsMoreThanMonth() throws Exception {
        mvc.perform(get("/api/availability?embedId=1234&productId=productTest&minTime=1595683643&maxTime=1598465043&interval=60"))
           .andExpect(status().isBadRequest());
    }

    @Test
    void shouldPerformRequestOnExternalApiAndSaveResponse() {
        ContentAvailableForEmbedResponse response = new ContentAvailableForEmbedResponse()
                .setCode(200)
                .setMessage(null)
                .setContentAvailable(true);
        when(externalAPI.findContentAvailableForEmbed(EMBED_ID, PRODUCT_ID)).thenReturn(response);
        int previousCount = availabilityRepository.findAll().size();

        await().atMost(Duration.FIVE_SECONDS)
               .untilAsserted(() -> {
                   verify(scheduler, times(1)).checkContentAvailableForEmbed();
                   assertEquals(previousCount + 1, availabilityRepository.findAll().size());
               });
    }
}
