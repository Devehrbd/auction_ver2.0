package org.zerock.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.domain.MemberVO;
import org.zerock.domain.ScoreVO;
@Mapper
public interface MemberMapper {
	public MemberVO readMember(String user_id);
	public void joinMember(MemberVO mVo);
	public void resignMember(String user_id);
	public void updateMember(MemberVO mVo);
	public void deleteBid_history(String userid);
	public void deleteTrade(String userid);
	
	
	public ScoreVO existScore(@Param("user_id")String user_id,@Param("product_id")int product_id);
}
