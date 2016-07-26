package com.cbsys.iclock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cbsys.iclock.repository.StaffFacePrintDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = IclockApplication.class)
@DirtiesContext
public class IclockApplicationTests {

	@Autowired
	private StaffFacePrintDao staffFacePrintDao;

	@Test
	public void contextLoads() {
	

	}

}
