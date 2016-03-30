package com.cbsys.iclock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbsys.iclock.domain.StaffFingerPrint;

public interface StaffFingerPrintDao extends JpaRepository<StaffFingerPrint, Long> {
	public List<StaffFingerPrint> findByPinAndFidAndCorpToken(String pin, int fid, String corpToken);

	public List<StaffFingerPrint> findByStaffid(Long staffid);
}
