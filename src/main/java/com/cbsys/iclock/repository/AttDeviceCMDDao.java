package com.cbsys.iclock.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cbsys.iclock.domain.AttDeviceCMD;

public interface AttDeviceCMDDao extends JpaRepository<AttDeviceCMD, Long> {
	@Query(value = "select cmd.serial_number ,count(cmd.id) as cmds from t_att_device_cmd as cmd where cmd.flag=1 GROUP BY cmd.serial_number", nativeQuery = true)
	public List<Object[]> findAllCMDStats();

	public List<AttDeviceCMD> findByCmdTypeAndSerialNumberAndFlagNot(int cmdType, String serialNumber, int flag);

	public AttDeviceCMD findOneByCmdSNAndSerialNumberAndCorpToken(long cmdSN, String serialNumber, String corpToken);

	@Query("select max(cmd.cmdSN) from AttDeviceCMD cmd where cmd.cmdSN >0 and  cmd.serialNumber=?1 and  cmd.corpToken=?2")
	public Long getCMDMaxSNByDevice(String serialNumber, String corpToken);

	@Query("select cmd from AttDeviceCMD cmd where cmd.serialNumber=?1 and  cmd.corpToken=?2 and cmd.flag=?3 and cmd.cmdType=?4 order by cmd.cmdSN desc")
	public List<AttDeviceCMD> getCmdsByDeviceAndFlagAndType(String serialNumber, String corpToken, int flag, int cmdType, Pageable pageable);

	@Query("select cmd from AttDeviceCMD cmd where cmd.serialNumber=?1 and  cmd.corpToken=?2 and cmd.flag=?3 and cmd.cmdType in (1,2,3,4,5,6,7,8,9,10,11,12,13,15,16,18,19,20)  order by cmd.cmdOrder asc, cmd.cmdSN asc")
	public List<AttDeviceCMD> getCmdsByDeviceAndFlag(String serialNumber, String corpToken, int flag, Pageable pageable);

}
