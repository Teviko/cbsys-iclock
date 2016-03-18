package com.cbsys.iclock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbsys.iclock.domain.Oplog;

public interface OplogDao extends JpaRepository<Oplog, Long> {

}
