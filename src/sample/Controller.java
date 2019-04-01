package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import io.swagger.model.AuthResult;
import io.swagger.model.AuthenticationVector;
import io.swagger.model.Av5GHeAka;
import io.swagger.model.EapSession;
import org.apache.ignite.IgniteCache;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UeAuthenticationsApiControllerTest2 {

    private MockMvc mockMvc;

    @InjectMocks
    UeAuthenticationsApiController ueAuthenticationsApi;

    @Mock
    public IgniteCache<String, AuthenticationVector> igniteCache;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(ueAuthenticationsApi).build();
    }

    @Test
    public void testHelloWorld() throws Exception {

        EapSession eapSession = new EapSession();/*
        eapSession.setAuthResult(AuthResult.SUCCESS);
        eapSession.setEapPayload("0101010101");
        eapSession.setKSeaf("sagasgda");
        eapSession.setSupi("05151515151515");*/


        mockMvc
                //.perform(MockMvcRequestBuilders.post("/nausf-auth/v1/ue-authentications/asdfgh/eap-session").contentType(MediaType.APPLICATION_JSON).content(new GsonBuilder().create().toJson(eapSession)))
                .perform(MockMvcRequestBuilders.get("/get"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello"));

    }

    @Test
    public void eapAuthMethod_test() throws Exception {

        EapSession eapSession = new EapSession();
        eapSession.setAuthResult(AuthResult.SUCCESS);
        eapSession.setEapPayload("0101010101");
        eapSession.setKSeaf("sagasgda");
        eapSession.setSupi("05151515151515");


        mockMvc
                .perform(MockMvcRequestBuilders.post("/nausf-auth/v1/ue-authentications/asdfgh/eap-session").contentType(MediaType.APPLICATION_JSON).content(new GsonBuilder().create().toJson(eapSession)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("hello"));

    }


    @Test
    public void testPost() throws Exception {

        mockMvc
                .perform(
                        post("/nausf-auth/v1/ue-authentications")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)) // içini doldurk json veri ike
                .andExpect(status().isOk());
    }

    //==================================TEST START ==================================
    @Test
    public void eapAuthMethod_test_() throws Exception {
        EapSession eapSession = new EapSession();
        eapSession.setAuthResult(AuthResult.SUCCESS);
        eapSession.setEapPayload("0101010101");
        eapSession.setKSeaf("sagasgda");
        eapSession.setSupi("05151515151515");
        AuthenticationVector authenticationVector=new AuthenticationVector();


        when(igniteCache.get("asdfgh")).thenReturn(authenticationVector);

        mockMvc
                .perform(post("/nausf-auth/v1/ue-authentications/asdfgh/eap-session").contentType(MediaType.APPLICATION_JSON).content(new GsonBuilder().create().toJson(eapSession)))
                .andExpect(status().isOk());

    }


    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
