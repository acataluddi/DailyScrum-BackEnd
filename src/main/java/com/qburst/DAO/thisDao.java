package com.qburst.DAO;

import java.sql.SQLException;
import java.util.List;

import com.qburst.Model.Data;

public interface thisDao {
	Data insertDataBase(Data data)throws SQLException;
	Data MemberProjectUpdate(Data data)throws SQLException;
	Data MemberTaskUpdate(Data data)throws SQLException;
	List<Data> readProjectNames()throws SQLException;
	List<Data> readEmployeeData()throws SQLException;
	List<Data> readProjectMemberData() throws SQLException;
	List<Data> readYesterdayTask() throws SQLException;
	List<Data> readTodayTask() throws SQLException;
	boolean login(Data data);
	List<Data> readData(int n)throws SQLException;
}