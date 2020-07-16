package com.assessme.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;

/**
 * @author Darshan Kathiriya
 * @created 31-May-2020 10:49 AM
 */
@SpringBootTest
public class MailSenderServiceTest {

  @Mock
  private MailSenderService emailService;


  @Test
  void sendMail() {
    emailService.sendSimpleMessage("john.doe@email.com",
        "Test", "Test Contest in Mail");

    verify(emailService, Mockito.timeout(1000).times(1))
        .sendSimpleMessage("john.doe@email.com",
            "Test", "Test Contest in Mail");

  }

}
