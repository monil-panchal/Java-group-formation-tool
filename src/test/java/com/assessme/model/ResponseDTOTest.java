package com.assessme.model;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * @author: hardik Created on: 2020-06-30
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@SuppressWarnings("deprecation")
public class ResponseDTOTest<T> {

    private final Logger logger = LoggerFactory.getLogger(ResponseDTOTest.class);

    @Test
    public void ConstructorTests() {
        logger.info("Running unit test for ResponseDTOTest constructor");
        Boolean success = true;
        String message = "ResponseDTOTest";
        String error = "Error";
        var data = (T) "data";

        ResponseDTO<T> responseDTO = new ResponseDTO<>(success, message, error, data);
        Assert.isTrue(responseDTO.getSuccess().equals(success));
        Assert.isTrue(responseDTO.getMessage().equals(message));
        Assert.isTrue(responseDTO.getError().equals(error));
        Assert.isTrue(responseDTO.getData().equals(data));

        ResponseDTO<T> responseDTO1 = new ResponseDTO<>();
        Assert.isNull(responseDTO1.getSuccess());
        Assert.isNull(responseDTO1.getMessage());
        Assert.isNull(responseDTO1.getError());
        Assert.isNull(responseDTO1.getData());
    }

    @Test
    public void getSuccessTest() {
        logger.info("Running unit test for fetching success from ResponseDTOTest");
        Boolean success = true;
        String message = "ResponseDTOTest";
        String error = "Error";
        var data = (T) "data";

        ResponseDTO<T> responseDTO = new ResponseDTO<>(success, message, error, data);
        Assert.isTrue(responseDTO.getSuccess().equals(success));
    }

    @Test
    public void setSuccessTest() {
        logger.info("Running unit test for setting success from ResponseDTOTest");
        Boolean success = true;
        String message = "ResponseDTOTest";
        String error = "Error";
        var data = (T) "data";

        ResponseDTO<T> responseDTO = new ResponseDTO<>(success, message, error, data);
        Assert.isTrue(responseDTO.getSuccess().equals(success));

        responseDTO.setSuccess(false);
        Assert.isTrue(responseDTO.getSuccess().equals(false));
    }

    @Test
    public void getMessageTest() {
        logger.info("Running unit test for fetching message from ResponseDTOTest");
        Boolean success = true;
        String message = "ResponseDTOTest";
        String error = "Error";
        var data = (T) "data";

        ResponseDTO<T> responseDTO = new ResponseDTO<>(success, message, error, data);
        Assert.isTrue(responseDTO.getMessage().equals(message));

        ResponseDTO<T> responseDTO1 = new ResponseDTO<T>();
        Assert.isNull(responseDTO1.getMessage());
    }

    @Test
    public void setMessageTest() {
        logger.info("Running unit test for setting message from ResponseDTOTest");
        Boolean success = true;
        String message = "ResponseDTOTest";
        String error = "Error";
        var data = (T) "data";

        ResponseDTO<T> responseDTO = new ResponseDTO<>(success, message, error, data);
        Assert.isTrue(responseDTO.getMessage().equals(message));

        String newMessage = "New Message";
        responseDTO.setMessage(newMessage);
        Assert.isTrue(responseDTO.getMessage().equals(newMessage));

    }

    @Test
    public void getErrorTest() {
        logger.info("Running unit test for fetching error from ResponseDTOTest");
        Boolean success = true;
        String message = "ResponseDTOTest";
        String error = "Error";
        var data = (T) "data";

        ResponseDTO<T> responseDTO = new ResponseDTO<>(success, message, error, data);
        Assert.isTrue(responseDTO.getError().equals(error));

        ResponseDTO<T> responseDTO1 = new ResponseDTO<T>();
        Assert.isNull(responseDTO1.getError());
    }

    @Test
    public void setErrorTest() {
        logger.info("Running unit test for setting error from ResponseDTOTest");
        Boolean success = true;
        String message = "ResponseDTOTest";
        String error = "Error";
        var data = (T) "data";

        ResponseDTO<T> responseDTO = new ResponseDTO<>(success, message, error, data);
        Assert.isTrue(responseDTO.getError().equals(error));

        String newError = "NewError";
        responseDTO.setError(newError);
        Assert.isTrue(responseDTO.getError().equals(newError));
    }

    @Test
    public void getDataTest() {
        logger.info("Running unit test for fetching data from ResponseDTOTest");
        Boolean success = true;
        String message = "ResponseDTOTest";
        String error = "Error";
        var data = (T) "data";

        ResponseDTO<T> responseDTO = new ResponseDTO<>(success, message, error, data);
        Assert.isTrue(responseDTO.getData().equals(data));

        ResponseDTO<T> responseDTO1 = new ResponseDTO<T>();
        Assert.isNull(responseDTO1.getData());
    }

    @Test
    public void setDataTest() {
        logger.info("Running unit test for setting data from ResponseDTOTest");
        Boolean success = true;
        String message = "ResponseDTOTest";
        String error = "Error";
        var data = (T) "data";

        ResponseDTO<T> responseDTO = new ResponseDTO<>(success, message, error, data);
        Assert.isTrue(responseDTO.getData().equals(data));

        var newData = (T) "newData";
        responseDTO.setData(newData);
        Assert.isTrue(responseDTO.getData().equals(newData));
    }
}
