package com.client1023;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * this class is for client to transfer data.
 * @author Charlotte
 *
 */
public class ChatData {
	String uname;
	DataInputStream dis;
	DataOutputStream dos;

	public ChatData(String uname,DataInputStream dis,DataOutputStream dos){
		this.uname = uname;
		this.dis = dis;
		this.dos = dos;
	}
}
