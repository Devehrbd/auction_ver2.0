package org.zerock.service;

import org.zerock.domain.MemberVO;
import org.zerock.domain.ScoreVO;

public interface MemberService {
	
	public MemberVO MemberRead(String user_id);
	public void joinMember(MemberVO mVo);
	public void resignMember(String user_id);
	public void updateMember(MemberVO mVo);
	public void deleteBid_history(String userid);
	public void deleteTrade(String userid);
	
	public ScoreVO existScore(String user_id, int product_id);
}
