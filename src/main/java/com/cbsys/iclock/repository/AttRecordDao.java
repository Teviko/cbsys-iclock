package com.cbsys.iclock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbsys.iclock.domain.AttRecord;

public interface AttRecordDao extends JpaRepository<AttRecord, Long> {
	public List<AttRecord> findTop100BySyncFlag(int syncFlag);
}
