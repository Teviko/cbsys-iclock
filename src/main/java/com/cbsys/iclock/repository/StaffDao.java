package com.cbsys.iclock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbsys.iclock.domain.Staff;

public interface StaffDao extends JpaRepository<Staff, Long> {
	public List<Staff> findByPinAndCorpToken(String pin, String corpToken);
}
