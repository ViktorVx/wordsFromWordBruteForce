package org.pva.wfwbf;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pva.wfwbf.service.BruteForceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.HashSet;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

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

//		WebClient client = WebClient.create();
//		WebClient.ResponseSpec responseSpec = client.get()
//				.uri("https://wordparts.ru/anagramma/?word=ДИАГРАММА")
//				.retrieve();
//		var html = responseSpec.bodyToMono(String.class).block();
//
//		var words = new HashSet<String>();
//		String str = String.format("<%s>(.*?)</%s>", "strong", "span");
//		Pattern pattern = Pattern.compile(str);
//		if (html != null) {
//			Matcher matcher = pattern.matcher(html);
//			while (matcher.find()) {
//				words.add(matcher.group(1));
//			}
//		}
//
//		words.forEach(log::info);


//		//***
//		words.forEach(log::info);
	}
}
