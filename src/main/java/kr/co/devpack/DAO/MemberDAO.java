package kr.co.devpack.DAO;

import java.util.ArrayList;

import kr.co.devpack.Members;


public interface MemberDAO {
 
    public ArrayList<Members> getMembers();
    public void insertMember(Members members);
    public ArrayList<Members> selectMember(Members members);
    public void updateMember(Members members);
    public void deleteMember(Members members);
    public void createMember();
    public void dropMember();
 
}
