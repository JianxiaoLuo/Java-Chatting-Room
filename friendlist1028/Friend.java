package com.friendlist1028;

public class Friend {
	private String name;
	private int number;
	private String pic;
	private String words;
	
	public Friend(String name, int number,String pic,String words) {
		super();
		this.name = name;
		this.number = number;
		this.pic = pic;
		this.words = words;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}
	
	
	
	
	
	
	

}
