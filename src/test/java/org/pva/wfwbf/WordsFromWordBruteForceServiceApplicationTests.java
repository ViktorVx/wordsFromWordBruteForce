package org.pva.wfwbf;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pva.wfwbf.service.BruteForceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BruteForceService.class)
@Slf4j
class WordsFromWordBruteForceServiceApplicationTests {

	@Autowired
	private BruteForceService bruteForceService;

	@Test
	void bruteForceTest() {
		log.info(bruteForceService.bruteForce("СЕМИНАРИСТ").toString());
	}
}
