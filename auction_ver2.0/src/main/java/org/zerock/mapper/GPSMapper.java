package org.zerock.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.zerock.domain.GPSVO;
@Mapper
public interface GPSMapper {
	public void registProductGPS(GPSVO gpsVo);
	public void deleteGPS(int product_id);
}
