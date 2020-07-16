package com.assessme.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Darshan Kathiriya
 * @created 15-July-2020 4:12 PM
 */
@ExtendWith(MockitoExtension.class)
class SurveyAlgorithmServiceTest {

    @Mock
    SurveyAlgorithmService surveyAlgorithmService;

    @Test
    void testTextToVector() {
        when(surveyAlgorithmService.convertFreeTextToVector("Hello World!"))
            .thenReturn(new double[]{708854109, 925923709, 0});
        double[] ints = surveyAlgorithmService.convertFreeTextToVector("Hello World!");
        Assertions.assertEquals(ints[0], 708854109);
        Assertions.assertEquals(ints[1], 925923709);
        Assertions.assertEquals(ints[2], 0);
    }

    @Test
    void cosineSimilarity() {
        double[] vec1 = new double[]{1, 1};
        double[] vec2 = new double[]{1, 1};
        when(surveyAlgorithmService.cosineSimilarity(vec1, vec2))
            .thenReturn(1.0);
        Assertions.assertEquals(surveyAlgorithmService.cosineSimilarity(vec1, vec2), 1);
    }
}