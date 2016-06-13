package kr.co.devpack.Mapper;

import java.util.ArrayList;

import kr.co.devpack.Boards;

public interface BoardMapper {
	ArrayList<Boards> getBoards();
	ArrayList<Boards> selectBoard(Boards board);
	void insertBoard(Boards board);
	void updateBoard(Boards board);
	void deleteBoard(Boards board);
	void createBoard();
	void dropBoard();
}
