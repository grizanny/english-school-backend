package com.arah.cwa.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

@Profile("testing")
@RunWith(SpringRunner.class)
@SpringBootTest
public class BackendApplicationTests {

	@Test
	public void contextLoads() {
	}

}
