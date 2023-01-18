package com.svi.san.specialization;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.svi.san.entity.Specialization;
import com.svi.san.repo.SpecializationRepository;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class SpecializationRepositoryTest {
	@Autowired
	private SpecializationRepository repo;
	
	/**
	 * test save operation
	 */
	
	@Test
	@Order(1)
	public void  testSpecCreate() {
		Specialization spec=new Specialization(null, "CSLD", "cardio", "dsgddh");
		spec=repo.save(spec);
		assertNotNull(spec.getId(),"Id must not be null");
		
		
	}
	
	/**
	 * test display all operation
	 */
	@Test
	@Order(2)
	public void testSpecFetchAll() {
		List<Specialization> specList=repo.findAll();
		assertNotNull(specList, "specList need not to be null");
		if(specList.isEmpty()) {
			fail("No data found in database ");
		}
		
		
	}

}
