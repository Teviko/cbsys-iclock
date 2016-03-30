package com.cbsys.iclock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbsys.iclock.domain.StaffFacePrint;

public interface StaffFacePrintDao extends JpaRepository<StaffFacePrint, Long> {
	public List<StaffFacePrint> findByPinAndFidAndCorpToken(String pin, int fid, String corpToken);

	public List<StaffFacePrint> findByStaffid(Long staffid);
}
