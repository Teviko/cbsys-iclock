package com.cbsys.iclock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cbsys.iclock.domain.AttDevice;

public interface AttDeviceDao extends JpaRepository<AttDevice, Long> {
	public AttDevice findBySerialNumber(String serialNumber);

	public List<AttDevice> findByCorpToken(String corpToken);

	@Query(value = "select ad.id, ad.serial_number, ad.device_model,IFNULL( UNIX_TIMESTAMP(ad.last_online_time)*1000,0) as dbLastOnlineTime, ad.offsite_att, ad.device_type,ad.corp_token,ad.time_zone_offset,ad.time_zone_id,ad.defaultWorkCode from t_att_devices as ad ", nativeQuery = true)
	public List<Object[]> findAllDevices();
}
