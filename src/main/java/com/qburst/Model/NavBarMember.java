package com.qburst.Model;

import java.util.Date;

public class NavBarMember implements Comparable<NavBarMember>{
	
	private String memberId;
	
	private String memberEmail;
	
	private String memberName;
	
	private String memberImage;
	
	private Date lastUpdate;
	
	private boolean hasNewUpdates;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberImage() {
		return memberImage;
	}

	public void setMemberImage(String memberImage) {
		this.memberImage = memberImage;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public boolean isHasNewUpdates() {
		return hasNewUpdates;
	}

	public void setHasNewUpdates(boolean hasNewUpdates) {
		this.hasNewUpdates = hasNewUpdates;
	}
	
    @Override
    public int compareTo(NavBarMember navMember) {
         return this.getLastUpdate().compareTo(((NavBarMember) navMember).getLastUpdate());
    }

}
