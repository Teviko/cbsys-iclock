package com.cbsys.iclock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbsys.iclock.domain.AttRecord;

public interface AttRecordDao extends JpaRepository<AttRecord, Long> {

}
