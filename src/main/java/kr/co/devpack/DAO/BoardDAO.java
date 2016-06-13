package kr.co.devpack.DAO;

import java.util.ArrayList;

import kr.co.devpack.Boards;

public interface BoardDAO  {
	public ArrayList<Boards> getBoards();
	public ArrayList<Boards> selectBoard(Boards board);
	public void insertBoard(Boards board);
	public void updateBoard(Boards board);
	public void deleteBoard(Boards board);
	public void createBoard();
	public void dropBoard();
}

