package com.qburst.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.qburst.Controller.IdTokenVerification;
import com.qburst.DAO.GoalDao;
import com.qburst.DAO.ScrumDao;
import com.qburst.Model.Comment;
import com.qburst.Model.Goal;
import com.qburst.Model.GoalMember;
import com.qburst.Model.NavBarMember;
import com.qburst.Model.UsersData;

public class GoalService {

	public Goal addGoal(Goal goal, String token) throws Exception {
		UsersData user = new UsersData();
		UsersData member = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		Goal newGoal = new Goal();
		ScrumDao sdao = new ScrumDao();
		GoalMember goalMember = new GoalMember();
		GoalMember gMember = new GoalMember();
		GoalDao gdao = new GoalDao();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("E dd-MM-yyyy 'at' hh:mm a ");
		String strDate = formatter.format(date);

		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {
				user = sdao.getIndividualUser(user.getEmail());
				member = sdao.getIndividualUser(goal.getUserEmail());
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Manager") && !member.getUserType().equals("Manager")) {
			try {
				goal.setGoalId(String.valueOf(date.getTime()));
				goal.setGoalTime(strDate);
				goal.setManagerEmail(user.getEmail());
				goal.setManagerName(user.getName());
				goal.setManagerImage(user.getImageurl());
				goalMember = gdao.getIndividualMemberGoal(goal.getUserEmail());
				goal.setComments(null);
				goal.setHasNewUpdates(true);
				if (goalMember != null && goal.getUserEmail().equals(goalMember.getUserEmail())) {
					//Goals already exist for the user so add the new one to array
					Goal[] previousGoalsArray = goalMember.getGoals();
					Goal[] newGoalArray = new Goal[previousGoalsArray.length + 1];
					System.arraycopy(previousGoalsArray, 0, newGoalArray, 0, previousGoalsArray.length);
					newGoalArray[previousGoalsArray.length] = goal;
					goalMember.setGoals(newGoalArray);
				} else {
					//Goals do not exist for the user, so add the new one
					GoalMember newMember = new GoalMember();
					if (member.getEmployeeID() != null && !member.getUserType().equals("Manager")) {
						newMember.setUserId(member.getEmployeeID());
						newMember.setUserEmail(member.getEmail());
						newMember.setUserName(member.getName());
						newMember.setUserImage(member.getImageurl());
						Goal[] goalsArray = new Goal[] { goal };
						newMember.setGoals(goalsArray);
						goalMember = newMember;
					} else {
						return null;
					}
				}
				goalMember.setHasNewUpdates(true);
				goalMember.setLastUpdate(strDate);
				gMember = gdao.insertGoal(goalMember);
				Goal[] newGoalArray = gMember.getGoals();
				//retrieving the last goal to check whether the insertion was successful
				newGoal = newGoalArray[newGoalArray.length - 1];
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return newGoal;
	}

	public Comment addComment(Comment comment, String token) throws Exception {
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		ScrumDao sdao = new ScrumDao();
		GoalMember goalMember = new GoalMember();
		GoalMember updatedGoalMember = new GoalMember();
		GoalDao gdao = new GoalDao();
		Comment addedComment = new Comment();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("E dd-MM-yyyy 'at' hh:mm a ");
		String strDate = formatter.format(date);

		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {
				user = sdao.getIndividualUser(user.getEmail());
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Manager") || user.getUserType().equals("User")) {
			try {
				goalMember = gdao.getIndividualMemberGoal(comment.getUserEmail());
				Goal[] oldGoalsArray = goalMember.getGoals();
				comment.setCommentId(String.valueOf(date.getTime()));
				comment.setCommentTime(strDate);
				comment.setMemberEmail(user.getEmail());
				comment.setMemberImage(user.getImageurl());
				comment.setMemberName(user.getName());
				if(goalMember!= null && (goalMember.getUserEmail().equals(comment.getUserEmail()))) {
					ArrayList<Goal> goalsList = new ArrayList<Goal>(Arrays.asList(oldGoalsArray));
					Iterator<Goal> itr = goalsList.iterator();
					while (itr.hasNext()) {
						Goal selectedGoal = (Goal) itr.next();
						if (selectedGoal.getGoalId().equals(comment.getGoalId())) {
							Comment[] oldCommentArray = selectedGoal.getComments();
							if(oldCommentArray!=null) {
								Comment[] newCommentArray = new Comment[oldCommentArray.length+1];
								System.arraycopy(oldCommentArray, 0, newCommentArray, 0, oldCommentArray.length);
								newCommentArray[oldCommentArray.length]=comment;
								selectedGoal.setComments(newCommentArray);
								selectedGoal.setHasNewUpdates(true);
							}else {
								Comment[] newCommentArray = new Comment[] { comment };
								selectedGoal.setComments(newCommentArray);
								selectedGoal.setHasNewUpdates(true);
							}
							itr.remove();
							goalsList.add(selectedGoal);
							break;
						}
					}
					oldGoalsArray = goalsList.toArray(oldGoalsArray);
					goalMember.setGoals(oldGoalsArray);
					goalMember.setHasNewUpdates(true);
					goalMember.setLastUpdate(strDate);
					updatedGoalMember = gdao.updateGoalMember(goalMember);
					Goal[] newGoalArray = updatedGoalMember.getGoals();
					for (Goal selectedGoal : newGoalArray) {
						if(selectedGoal.getGoalId().equals(comment.getGoalId())) {
							Comment[] newCommentsArray = selectedGoal.getComments();
							addedComment = newCommentsArray[newCommentsArray.length-1];
							break;
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}		
		return addedComment;
	}
	
	public List<NavBarMember> readGoalStatus(String token) throws Exception {
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		ScrumDao sdao = new ScrumDao();
		GoalDao gdao = new GoalDao();
		List<NavBarMember> membersStatusList = new ArrayList<NavBarMember>();

		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {
				user = sdao.getIndividualUser(user.getEmail());
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("User")) {
			membersStatusList = gdao.readGoalStatusForUser(user.getEmail());
		}else if (user.getUserType().equals("Manager")) {
			
		}
		return membersStatusList;		
	}
	
	public GoalMember getGoalMember(String token, String userEmail) throws Exception {
		GoalMember goalMember = new GoalMember();
		UsersData member = new UsersData();
		UsersData user = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		ScrumDao sdao = new ScrumDao();
		GoalDao gdao = new GoalDao();
		
		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {
				user = sdao.getIndividualUser(user.getEmail());
				member = sdao.getIndividualUser(userEmail);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("User")) {
			
		}else if (user.getUserType().equals("Manager") && !member.getUserType().equals("Manager")) {
			goalMember = gdao.getIndividualMemberGoal(userEmail);
		}
		return goalMember;		
	}
}
