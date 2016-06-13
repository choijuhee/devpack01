package kr.co.devpack.Mapper;

import java.util.ArrayList;

import kr.co.devpack.Members;

public interface MemberMapper {
    ArrayList<Members> getMembers();
    ArrayList<Members> selectMember(Members members);
    void insertMember(Members members);
    void updateMember(Members members);
    void deleteMember(Members members);
    void createMember();
    void dropMember();
}
