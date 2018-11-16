package com.qburst.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {
				user = sdao.getIndividualUser(user.getEmail());
				member = sdao.getIndividualUser(goal.getUserEmail());
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if (user.getUserType().equals("Manager") && member.getUserType().equals("User")) {
			try {
				goal.setGoalId(String.valueOf(date.getTime()));
				goal.setGoalTime(date);
				goal.setManagerEmail(user.getEmail());
				goal.setManagerName(user.getName());
				goal.setManagerImage(user.getImageurl());
				goalMember = gdao.getIndividualMemberGoal(goal.getUserEmail());
				goal.setComments(null);
				goal.sethasNewUpdatesForManager(false);
				goal.sethasNewUpdatesForUser(true);
				if (goalMember != null && goal.getUserEmail().equals(goalMember.getUserEmail())) {
					// Goals already exist for the user so add the new one to array
					Goal[] previousGoalsArray = goalMember.getGoals();
					Goal[] newGoalArray = new Goal[previousGoalsArray.length + 1];
					System.arraycopy(previousGoalsArray, 0, newGoalArray, 0, previousGoalsArray.length);
					newGoalArray[previousGoalsArray.length] = goal;
					goalMember.setGoals(newGoalArray);
				} else {
					// Goals do not exist for the user, so add the new one
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
				goalMember.setLastUpdate(date);
				Goal[] goalsArray = goalMember.getGoals();
				ArrayList<Goal> goalsList = new ArrayList<Goal>(Arrays.asList(goalsArray));
				Collections.sort(goalsList, Collections.reverseOrder());
				goalsArray = goalsList.toArray(goalsArray);
				goalMember.setGoals(goalsArray);
				gMember = gdao.insertGoal(goalMember);
				Goal[] newGoalArray = gMember.getGoals();
				// retrieving the last added goal to check whether the insertion was successful
				for (Goal selectedGoal : newGoalArray) {
					if (selectedGoal.getGoalId().equals(goal.getGoalId())) {
						newGoal = selectedGoal;
						break;
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return newGoal;
	}

	public Comment addComment(Comment comment, String token) throws Exception {
		UsersData user = new UsersData();
		UsersData member = new UsersData();
		IdTokenVerification id_verifier = new IdTokenVerification();
		ScrumDao sdao = new ScrumDao();
		GoalMember goalMember = new GoalMember();
		GoalMember updatedGoalMember = new GoalMember();
		GoalDao gdao = new GoalDao();
		Comment addedComment = new Comment();
		Date date = new Date();

		user = id_verifier.processToken(token);
		if (user.getEmployeeID() != null) {
			try {
				user = sdao.getIndividualUser(user.getEmail());
				member = sdao.getIndividualUser(comment.getUserEmail());
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if ((user.getUserType().equals("Manager") || user.getUserType().equals("User"))
				&& (!member.getUserType().equals("Manager"))) {
			try {
				goalMember = gdao.getIndividualMemberGoal(comment.getUserEmail());
				Goal[] oldGoalsArray = goalMember.getGoals();
				comment.setCommentId(String.valueOf(date.getTime()));
				comment.setCommentTime(date);
				comment.setMemberEmail(user.getEmail());
				comment.setMemberImage(user.getImageurl());
				comment.setMemberName(user.getName());
				if (goalMember != null && (goalMember.getUserEmail().equals(comment.getUserEmail()))) {
					ArrayList<Goal> goalsList = new ArrayList<Goal>(Arrays.asList(oldGoalsArray));
					Iterator<Goal> itr = goalsList.iterator();
					boolean isValidAuthor = false;
					while (itr.hasNext()) {
						Goal selectedGoal = (Goal) itr.next();
						if (selectedGoal.getGoalId().equals(comment.getGoalId())
								&& (selectedGoal.getManagerEmail().equals(user.getEmail())
										|| selectedGoal.getUserEmail().equals(user.getEmail()))) {
							isValidAuthor = true;
							Comment[] oldCommentArray = selectedGoal.getComments();
							if (oldCommentArray != null) {
								Comment[] newCommentArray = new Comment[oldCommentArray.length + 1];
								System.arraycopy(oldCommentArray, 0, newCommentArray, 0, oldCommentArray.length);
								newCommentArray[oldCommentArray.length] = comment;
								selectedGoal.setComments(newCommentArray);
								if (user.getUserType().equals("User")) {
									selectedGoal.sethasNewUpdatesForManager(true);
									selectedGoal.sethasNewUpdatesForUser(false);
								} else if (user.getUserType().equals("Manager")) {
									selectedGoal.sethasNewUpdatesForManager(false);
									selectedGoal.sethasNewUpdatesForUser(true);
								}
							} else {
								Comment[] newCommentArray = new Comment[] { comment };
								selectedGoal.setComments(newCommentArray);
								if (user.getUserType().equals("User")) {
									selectedGoal.sethasNewUpdatesForManager(true);
									selectedGoal.sethasNewUpdatesForUser(false);
								} else if (user.getUserType().equals("Manager")) {
									selectedGoal.sethasNewUpdatesForManager(false);
									selectedGoal.sethasNewUpdatesForUser(true);
								}
							}
							itr.remove();
							goalsList.add(selectedGoal);
							break;
						}
					}
					if (isValidAuthor) {
						Collections.sort(goalsList, Collections.reverseOrder());
						oldGoalsArray = goalsList.toArray(oldGoalsArray);
						goalMember.setGoals(oldGoalsArray);
						goalMember.setHasNewUpdates(true);
						goalMember.setLastUpdate(date);
						updatedGoalMember = gdao.updateGoalMember(goalMember);
						Goal[] newGoalArray = updatedGoalMember.getGoals();
						for (Goal selectedGoal : newGoalArray) {
							if (selectedGoal.getGoalId().equals(comment.getGoalId())) {
								Comment[] newCommentsArray = selectedGoal.getComments();
								addedComment = newCommentsArray[newCommentsArray.length - 1];
								break;
							}
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
		try {
			if (user.getUserType().equals("User")) {
				membersStatusList = gdao.readGoalStatusForUser(user.getEmail());
				Collections.sort(membersStatusList, Collections.reverseOrder());
				for (NavBarMember navMember : membersStatusList) {
					String currentManagerEmail = navMember.getMemberEmail();
					UsersData currentMember = sdao.getIndividualUser(currentManagerEmail);
					navMember.setMemberId(currentMember.getEmployeeID());
				}
			} else if (user.getUserType().equals("Manager")) {
				String[] members = gdao.getMembersUnderManager(user.getEmail());
				for (String memberEmail : members) {
					UsersData currentMember = sdao.getIndividualUser(memberEmail);
					if (currentMember.getEmployeeID() != null && currentMember.getUserType().equals("User")) {
						GoalMember gMember = gdao.getIndividualMemberGoal(memberEmail);
						if (gMember != null) {
							boolean hasUpdatesForManager = false;
							Goal[] goalsArray = gMember.getGoals();
							String managerEmail = user.getEmail();
							for (Goal g : goalsArray) {
//								Updating the 'hasNewUpdates' indicator by iterating through the goals created by the manager
								if (g.getManagerEmail().equals(managerEmail)) {
									if (g.gethasNewUpdatesForManager()) {
										hasUpdatesForManager = true;
									}
								}
							}
							NavBarMember navMember = new NavBarMember();
							navMember.setMemberId(gMember.getUserId());
							navMember.setMemberEmail(gMember.getUserEmail());
							navMember.setMemberName(gMember.getUserName());
							navMember.setMemberImage(gMember.getUserImage());
							navMember.setLastUpdate(gMember.getLastUpdate());
							navMember.setHasNewUpdates(hasUpdatesForManager);
							membersStatusList.add(navMember);
						} else {
							NavBarMember navMember = new NavBarMember();
							navMember.setMemberId(currentMember.getEmployeeID());
							navMember.setMemberEmail(currentMember.getEmail());
							navMember.setMemberImage(currentMember.getImageurl());
							navMember.setMemberName(currentMember.getName());
							navMember.setHasNewUpdates(false);
							Date d2 = new Date(0);
							navMember.setLastUpdate(d2);
							membersStatusList.add(navMember);
						}
					}
				}
				Collections.sort(membersStatusList, Collections.reverseOrder());
			}
		} catch (Exception e) {
			System.out.println(e);
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
		if (user.getUserType().equals("User") && member.getUserType().equals("Manager")) {
			goalMember = gdao.getIndividualMemberGoal(user.getEmail());
			Goal[] goalsArray = goalMember.getGoals();
			ArrayList<Goal> goalsList = new ArrayList<Goal>(Arrays.asList(goalsArray));
			Iterator<Goal> itr = goalsList.iterator();
			while (itr.hasNext()) {
				Goal selectedGoal = (Goal) itr.next();
				if (!selectedGoal.getManagerEmail().equals(userEmail)) {
					itr.remove();
				}
			}
			Goal[] newGoalsArray = new Goal[goalsList.size()];
			newGoalsArray = goalsList.toArray(newGoalsArray);
			goalMember.setUserEmail(member.getEmail());
			goalMember.setUserId(member.getEmployeeID());
			goalMember.setUserName(member.getName());
			goalMember.setUserImage(member.getImageurl());
			goalMember.setGoals(newGoalsArray);
			changeGoalReadStatus(user, member);
		} else if (user.getUserType().equals("Manager") && member.getUserType().equals("User")) {
			goalMember = gdao.getIndividualMemberGoal(userEmail);
			changeGoalReadStatus(user, member);
		}
		return goalMember;
	}

	public boolean changeGoalReadStatus(UsersData user, UsersData member) throws Exception {
		GoalMember goalMember = new GoalMember();
		GoalDao gdao = new GoalDao();
		try {
			if (user.getUserType().equals("Manager") && member.getUserType().equals("User")) {
				goalMember = gdao.getIndividualMemberGoal(member.getEmail());
				String managerEmail = user.getEmail();
				Goal[] goalsArray = goalMember.getGoals();
				Goal[] newGoalsArray = new Goal[goalsArray.length];
				int newGoalsArrayIndex = 0;
				for (Goal goal : goalsArray) {
					if (goal.getManagerEmail().equals(managerEmail)) {
						goal.sethasNewUpdatesForManager(false);
					}
					newGoalsArray[newGoalsArrayIndex] = goal;
					newGoalsArrayIndex++;
				}
				goalMember.setGoals(newGoalsArray);
				GoalMember gMember = gdao.updateGoalMember(goalMember);
				if (gMember.getId() != null) {
					return true;
				}
			} else if (user.getUserType().equals("User") && member.getUserType().equals("Manager")) {
				goalMember = gdao.getIndividualMemberGoal(user.getEmail());
				String managerEmail = member.getEmail();
				Goal[] goalsArray = goalMember.getGoals();
				Goal[] newGoalsArray = new Goal[goalsArray.length];
				int newGoalsArrayIndex = 0;
				for (Goal goal : goalsArray) {
					if (goal.getManagerEmail().equals(managerEmail)) {
						goal.sethasNewUpdatesForUser(false);
					}
					newGoalsArray[newGoalsArrayIndex] = goal;
					newGoalsArrayIndex++;
				}
				goalMember.setGoals(newGoalsArray);
				GoalMember gMember = gdao.updateGoalMember(goalMember);
				if (gMember.getId() != null) {
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}
}
