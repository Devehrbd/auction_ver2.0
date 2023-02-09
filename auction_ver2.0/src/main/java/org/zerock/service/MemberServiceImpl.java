package org.zerock.service;

import org.springframework.stereotype.Service;
import org.zerock.domain.MemberVO;
import org.zerock.domain.ScoreVO;
import org.zerock.mapper.MemberMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	private MemberMapper mMapper;
	
	public MemberVO MemberRead(String user_id) {
		
		return mMapper.readMember(user_id);
		
	}

	@Override
	public void joinMember(MemberVO mVo) {
		mMapper.joinMember(mVo);
	}

	@Override
	public void resignMember(String user_id) {
		mMapper.resignMember(user_id);
	}

	@Override
	public void updateMember(MemberVO mVo) {
		mMapper.updateMember(mVo);
		
	}

	@Override
	public void deleteBid_history(String userid) {
		mMapper.deleteBid_history(userid);
	}

	@Override
	public void deleteTrade(String userid) {
		mMapper.deleteTrade(userid);
		
	}
	@Override
	public ScoreVO existScore(String user_id, int product_id) {
		
		return mMapper.existScore(user_id, product_id);
	}
}
