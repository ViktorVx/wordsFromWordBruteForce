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
//		var urls = urlsText.split("\n");
//		var words = new HashSet<String>();
//		WebClient client = WebClient.create();
//		for (String url : urls) {
//			WebClient.ResponseSpec responseSpec = client.get()
//					.uri(url)
//					.retrieve();
//			var html = responseSpec.bodyToMono(String.class).block();
//
//			String str = String.format("<%s>(.*?)</%s>", "strong", "strong");
//			Pattern pattern = Pattern.compile(str);
//			if (html != null) {
//				Matcher matcher = pattern.matcher(html);
//				while (matcher.find()) {
//					words.add(matcher.group(1));
//				}
//			}
//		}
//		//***
//		words.forEach(log::info);
	}
}
